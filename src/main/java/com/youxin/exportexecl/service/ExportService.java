package com.youxin.exportexecl.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.media.sound.InvalidDataException;
import com.youxin.exportexecl.bean.*;
import com.youxin.exportexecl.common.FileNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author rushicheng
 * @create 2020/8/19 11:08
 * @Description:
 */
@Service
@Slf4j
public class ExportService {

    public final static String FILE_PATH = "E:\\test\\";

    /**
     * 导出excel
     * @param readFileName
     * @return
     * @throws InvalidDataException
     */
    public String export(String readFileName) throws InvalidDataException {

        if(!readFileName.endsWith(".json")){
            log.error("文件非.json结尾，不符合要求,name:{}", readFileName);
            throw new InvalidDataException("文件非.json结尾，不符合要求");
        }

        //逐行读取/解析json文件
        String inputFileName = FILE_PATH + File.separator + readFileName;

        File readFile = new File(inputFileName);

        if(!readFile.exists()){
            log.error("输入文件不存在，inputFileName:{}",inputFileName);
            throw new InvalidDataException("输入文件不存在，inputFileName:"+inputFileName);
        }

        String outputFileName = FILE_PATH + File.separator + FileNameUtils.getFileNameNoEx(readFileName) + ".xlsx";

        ExcelWriter excelWriter = EasyExcel.write(outputFileName).build();

        try {
            List<String> jsons = FileUtils.readLines(readFile, "UTF-8");

            this.generateExecl(jsons, excelWriter);
        } catch (IOException e) {
            log.error("export error,e:",e);
            e.printStackTrace();
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        return outputFileName;
    }


    /**
     * 生成execl
     * @param jsons
     * @param excelWriter
     */
    private void generateExecl(List<String> jsons, ExcelWriter excelWriter){


        /**
         * 1.生成待写入的sheet
         */
        WriteSheet baihangDataSheet = null;
        WriteSheet reportHeaderSheet = null;
        WriteSheet personalProfileSheet = null;
        WriteSheet homeInfoSheet = null;
        WriteSheet workInfoSheet = null;
        WriteSheet nonRevolvingLoanSummarySheet = null;
        WriteSheet nonRevolvingLoanD30Sheet = null;
        WriteSheet nonRevolvingLoanD60Sheet = null;
        WriteSheet nonRevolvingLoanD90Sheet = null;
        WriteSheet nonRevolvingLoanD160Sheet = null;
        WriteSheet nonRevolvingLoanD180Sheet = null;
        WriteSheet nonRevolvingLoanD360Sheet = null;
        WriteSheet revolvingLoanSummarySheet = null;
        WriteSheet revolvingLoanD30Sheet = null;
        WriteSheet revolvingLoanD60Sheet = null;
        WriteSheet revolvingLoanD90Sheet = null;
        WriteSheet revolvingLoanD160Sheet = null;
        WriteSheet revolvingLoanD180Sheet = null;
        WriteSheet revolvingLoanD360Sheet = null;
        WriteSheet queryHistorySheet = null;

        /**
         * 2.写入sheet数据
         */
        for(String json:jsons){

            if(StringUtils.isBlank(json)){
                log.info("存在空行，跳过");
                continue;
            }

            JSONObject jsonObject = JSON.parseObject(json);

            String dataId = UUID.randomUUID().toString().replace("-", "");

            JSONObject dataObject = jsonObject.getJSONObject("data").getJSONObject("data");

            //1.BaihangData
            BaihangData baihangData = ExportService.buildBaihangData(jsonObject, dataId);

            List<BaihangData> datas = new ArrayList<>();
            datas.add(baihangData);

            if(baihangDataSheet == null){
                baihangDataSheet = EasyExcel.writerSheet("baihangData").head(BaihangData.class).build();
            }

            excelWriter.write(datas, baihangDataSheet);

            //2.ReportHeader
            if(dataObject.containsKey("reportHeader")){
                ReportHeader reportHeader = dataObject.getObject("reportHeader", ReportHeader.class);
                reportHeader.setDataId(dataId);
                List<ReportHeader> reportHeaders = new ArrayList<>();
                reportHeaders.add(reportHeader);

                if(reportHeaderSheet == null){
                    reportHeaderSheet = EasyExcel.writerSheet( "reportHeader").head(ReportHeader.class).build();
                }
                excelWriter.write(reportHeaders, reportHeaderSheet);
            }

            //3.PersonalProfile
            if(dataObject.containsKey("personalProfile")){
                PersonalProfile personalProfile = dataObject.getObject("personalProfile", PersonalProfile.class);
                personalProfile.setDataId(dataId);
                List<PersonalProfile> personalProfiles = new ArrayList<>();
                personalProfiles.add(personalProfile);

                if(personalProfileSheet == null){
                    personalProfileSheet = EasyExcel.writerSheet( "personalProfile").head(PersonalProfile.class).build();
                }

                excelWriter.write(personalProfiles, personalProfileSheet);
            }

            //4.HomeInfo
            if(dataObject.containsKey("homeInfo")){
                List<HomeInfo> homeInfos = JSONArray.parseArray(dataObject.getString("homeInfo"), HomeInfo.class);
                homeInfos.stream().forEach(t->t.setDataId(dataId));

                if(homeInfoSheet == null){
                    homeInfoSheet = EasyExcel.writerSheet( "homeInfo").head(HomeInfo.class).build();
                }

                excelWriter.write(homeInfos, homeInfoSheet);
            }

            //5.WorkInfo
            if(dataObject.containsKey("workInfo")){
                List<WorkInfo> workInfos = JSONArray.parseArray(dataObject.getString("workInfo"), WorkInfo.class);
                workInfos.stream().forEach(t->t.setDataId(dataId));

                if(workInfoSheet == null){
                    workInfoSheet = EasyExcel.writerSheet("workInfo").head(WorkInfo.class).build();
                }
                excelWriter.write(workInfos, workInfoSheet);
            }

            //6.nonRevolvingLoan
            if (dataObject.containsKey("nonRevolvingLoan")){
                JSONObject nonRevolvingLoan = dataObject.getJSONObject("nonRevolvingLoan");

                //summary
                if (nonRevolvingLoan.containsKey("summary")){
                    NonRevolvingLoanSummary summary = nonRevolvingLoan.getObject("summary", NonRevolvingLoanSummary.class);
                    summary.setDataId(dataId);

                    List<NonRevolvingLoanSummary> summaries = new ArrayList<>();
                    summaries.add(summary);

                    if(nonRevolvingLoanSummarySheet == null){
                        nonRevolvingLoanSummarySheet = EasyExcel.writerSheet("NonRevolvingLoan.Summary").head(NonRevolvingLoanSummary.class).build();
                    }
                    excelWriter.write(summaries, nonRevolvingLoanSummarySheet);
                }

                //D30
                if (nonRevolvingLoan.containsKey("D30")){
                    NonRevolvingLoanD30 D30 = nonRevolvingLoan.getObject("D30", NonRevolvingLoanD30.class);
                    D30.setDataId(dataId);

                    List<NonRevolvingLoanD30> D30s = new ArrayList<>();
                    D30s.add(D30);

                    if(nonRevolvingLoanD30Sheet== null){
                        nonRevolvingLoanD30Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D30").head(NonRevolvingLoanD30.class).build();
                    }
                    excelWriter.write(D30s, nonRevolvingLoanD30Sheet);
                }

                //D60
                if (nonRevolvingLoan.containsKey("D60")){
                    NonRevolvingLoanD60 D60 = nonRevolvingLoan.getObject("D60", NonRevolvingLoanD60.class);
                    D60.setDataId(dataId);

                    List<NonRevolvingLoanD60> D60s = new ArrayList<>();
                    D60s.add(D60);

                    if(nonRevolvingLoanD60Sheet ==null){
                        nonRevolvingLoanD60Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D60").head(NonRevolvingLoanD60.class).build();
                    }
                    excelWriter.write(D60s, nonRevolvingLoanD60Sheet);
                }

                //D90
                if (nonRevolvingLoan.containsKey("D90")){
                    NonRevolvingLoanD90 D90 = nonRevolvingLoan.getObject("D90", NonRevolvingLoanD90.class);
                    D90.setDataId(dataId);

                    List<NonRevolvingLoanD90> D90s = new ArrayList<>();
                    D90s.add(D90);

                    if(nonRevolvingLoanD90Sheet ==null){
                        nonRevolvingLoanD90Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D90").head(NonRevolvingLoanD90.class).build();
                    }
                    excelWriter.write(D90s, nonRevolvingLoanD90Sheet);
                }

                //D160
                if (nonRevolvingLoan.containsKey("D160")){
                    NonRevolvingLoanD160 D160 = nonRevolvingLoan.getObject("D160", NonRevolvingLoanD160.class);
                    D160.setDataId(dataId);

                    List<NonRevolvingLoanD160> D160s = new ArrayList<>();
                    D160s.add(D160);
                    if(nonRevolvingLoanD160Sheet==null){
                        nonRevolvingLoanD160Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D160").head(NonRevolvingLoanD160.class).build();
                    }
                    excelWriter.write(D160s, nonRevolvingLoanD160Sheet);
                }

                //D180
                if (nonRevolvingLoan.containsKey("D180")){
                    NonRevolvingLoanD180 D180 = nonRevolvingLoan.getObject("D180", NonRevolvingLoanD180.class);
                    D180.setDataId(dataId);

                    List<NonRevolvingLoanD180> D180s = new ArrayList<>();
                    D180s.add(D180);
                    if(nonRevolvingLoanD180Sheet==null){
                        nonRevolvingLoanD180Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D180").head(NonRevolvingLoanD180.class).build();
                    }
                    excelWriter.write(D180s, nonRevolvingLoanD180Sheet);
                }

                //D360
                if (nonRevolvingLoan.containsKey("D360")){
                    NonRevolvingLoanD360 D360 = nonRevolvingLoan.getObject("D360", NonRevolvingLoanD360.class);
                    D360.setDataId(dataId);

                    List<NonRevolvingLoanD360> D360s = new ArrayList<>();
                    D360s.add(D360);

                    if(nonRevolvingLoanD360Sheet == null){
                        nonRevolvingLoanD360Sheet = EasyExcel.writerSheet("NonRevolvingLoan.D360").head(NonRevolvingLoanD360.class).build();
                    }
                    excelWriter.write(D360s, nonRevolvingLoanD360Sheet);
                }

            }

            //7.revolvingLoan
            if (dataObject.containsKey("revolvingLoan")){
                JSONObject revolvingLoan = dataObject.getJSONObject("revolvingLoan");

                //summary
                if (revolvingLoan.containsKey("summary")){
                    RevolvingLoanSummary revolvingLoanSummary = revolvingLoan.getObject("summary", RevolvingLoanSummary.class);
                    revolvingLoanSummary.setDataId(dataId);

                    List<RevolvingLoanSummary> revolvingLoanSummarys = new ArrayList<>();
                    revolvingLoanSummarys.add(revolvingLoanSummary);

                    if(revolvingLoanSummarySheet == null){
                        revolvingLoanSummarySheet = EasyExcel.writerSheet("RevolvingLoan.Summary").head(RevolvingLoanSummary.class).build();
                    }
                    excelWriter.write(revolvingLoanSummarys, revolvingLoanSummarySheet);
                }

                //D30
                if (revolvingLoan.containsKey("D30")){
                    RevolvingLoanD30 revolvingLoanD30 = revolvingLoan.getObject("D30", RevolvingLoanD30.class);
                    revolvingLoanD30.setDataId(dataId);

                    List<RevolvingLoanD30> revolvingLoanD30s = new ArrayList<>();
                    revolvingLoanD30s.add(revolvingLoanD30);

                    if(revolvingLoanD30Sheet==null){
                        revolvingLoanD30Sheet = EasyExcel.writerSheet("RevolvingLoan.D30").head(RevolvingLoanD30.class).build();
                    }
                    excelWriter.write(revolvingLoanD30s, revolvingLoanD30Sheet);
                }

                //D60
                if (revolvingLoan.containsKey("D60")){
                    RevolvingLoanD60 revolvingLoanD60 = revolvingLoan.getObject("D60", RevolvingLoanD60.class);
                    revolvingLoanD60.setDataId(dataId);

                    List<RevolvingLoanD60> revolvingLoanD60s = new ArrayList<>();
                    revolvingLoanD60s.add(revolvingLoanD60);

                    if(revolvingLoanD60Sheet==null){
                        revolvingLoanD60Sheet = EasyExcel.writerSheet("RevolvingLoan.D60").head(RevolvingLoanD60.class).build();
                    }
                    excelWriter.write(revolvingLoanD60s, revolvingLoanD60Sheet);
                }

                //D90
                if (revolvingLoan.containsKey("D90")){
                    RevolvingLoanD90 revolvingLoanD90 = revolvingLoan.getObject("D90", RevolvingLoanD90.class);
                    revolvingLoanD90.setDataId(dataId);

                    List<RevolvingLoanD90> revolvingLoanD90s = new ArrayList<>();
                    revolvingLoanD90s.add(revolvingLoanD90);

                    if(revolvingLoanD90Sheet==null){
                        revolvingLoanD90Sheet = EasyExcel.writerSheet("RevolvingLoan.D90").head(RevolvingLoanD90.class).build();
                    }
                    excelWriter.write(revolvingLoanD90s, revolvingLoanD90Sheet);
                }

                //D160
                if (revolvingLoan.containsKey("D160")){
                    RevolvingLoanD160 revolvingLoanD160 = revolvingLoan.getObject("D160", RevolvingLoanD160.class);
                    revolvingLoanD160.setDataId(dataId);

                    List<RevolvingLoanD160> revolvingLoanD160s = new ArrayList<>();
                    revolvingLoanD160s.add(revolvingLoanD160);
                    if(revolvingLoanD160Sheet==null){
                        revolvingLoanD160Sheet = EasyExcel.writerSheet("RevolvingLoan.D160").head(RevolvingLoanD160.class).build();
                    }
                    excelWriter.write(revolvingLoanD160s, revolvingLoanD160Sheet);
                }

                //D180
                if (revolvingLoan.containsKey("D180")){
                    RevolvingLoanD180 revolvingLoanD180 = revolvingLoan.getObject("D180", RevolvingLoanD180.class);
                    revolvingLoanD180.setDataId(dataId);

                    List<RevolvingLoanD180> revolvingLoanD180s = new ArrayList<>();
                    revolvingLoanD180s.add(revolvingLoanD180);
                    if(revolvingLoanD180Sheet==null){
                        revolvingLoanD180Sheet = EasyExcel.writerSheet("RevolvingLoan.D180").head(RevolvingLoanD180.class).build();
                    }
                    excelWriter.write(revolvingLoanD180s, revolvingLoanD180Sheet);
                }

                //D360
                if (revolvingLoan.containsKey("D360")){
                    RevolvingLoanD360 revolvingLoanD360 = revolvingLoan.getObject("D360", RevolvingLoanD360.class);
                    revolvingLoanD360.setDataId(dataId);

                    List<RevolvingLoanD360> revolvingLoanD360s = new ArrayList<>();
                    revolvingLoanD360s.add(revolvingLoanD360);

                    if (revolvingLoanD360Sheet == null){
                        revolvingLoanD360Sheet = EasyExcel.writerSheet("RevolvingLoan.D360").head(RevolvingLoanD360.class).build();
                    }
                    excelWriter.write(revolvingLoanD360s, revolvingLoanD360Sheet);
                }

            }

            //8.QueryHistory
            if(dataObject.containsKey("queryHistory")){
                List<QueryHistory> queryHistorys = JSONArray.parseArray(dataObject.getString("queryHistory"), QueryHistory.class);
                queryHistorys.stream().forEach(t->t.setDataId(dataId));

                if(queryHistorySheet == null){
                    queryHistorySheet = EasyExcel.writerSheet("queryHistory").head(QueryHistory.class).build();
                }
                excelWriter.write(queryHistorys, queryHistorySheet);
            }
        }

    }

    private static BaihangData buildBaihangData(JSONObject jsonObject, String dataId){

        JSONObject data = jsonObject.getJSONObject("data");

        return BaihangData.builder().applicationId(data.getString("applicationId"))
                .dataId(dataId).mobile(data.getString("mobile")).name(data.getString("name"))
                .queryReason(data.getString("queryReason")).idno(data.getString("idno")).loanKey(jsonObject.getString("loanKey"))
                .idNumber(jsonObject.getString("idNumber")).job(jsonObject.getString("job")).type(jsonObject.getString("type")).timestamp(jsonObject.getString("timestamp"))
                .build();
    }

}
