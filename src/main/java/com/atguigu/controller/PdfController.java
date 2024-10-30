package com.atguigu.controller;


import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pdf模板文件使用demo
 */
@Slf4j
@RequestMapping("/pdf")
@Controller
public class PdfController {
    @RequestMapping("/download")
    public void downloadPdf(HttpServletResponse response) {
        String resourcePath = getClass().getClassLoader().getResource("/").getPath();
        String inputFileName = resourcePath + "pdf_template/report_tmplate.pdf";
        String fontPath = resourcePath + "pdf_template/Font/HEITI_GBK.TTF";

        // 填充表单的数据(文本)
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("examineTypeReportName", "CT检查报告单");
        data.put("examineTypeNo", "CT号:");
        data.put("patientId", "111111");
        data.put("hosName", "河北医院河北医院河北医院河北医院");
        data.put("curDate", "2021/12/12 10:44");
        data.put("ctNo", "xxxxx");
        data.put("patientName", "张三");
        data.put("patientSex", "男");
        data.put("patientAge", "66");
        data.put("admissionId", "11111111");
        data.put("location", "25");
        data.put("applyDeptName", "外科");
        data.put("examineNumber", "999999");
        data.put("examineItem", "我从小就喜欢读书。为了不被妈妈发现，所以常常是躲在被窝里看书。后来，我走进了一座座金碧辉煌的");
        data.put("examineDate", "2024-12-12");
        data.put("imageSight", "我从小就喜欢读书。为了不被妈妈发现，所以常常是躲在被窝里看书。后来，我走进了一座座金碧辉煌的宫殿，我畅游了让人心生遐想的海底世界和苍茫无际的宇宙空间。随着年龄的增大，我便顺着鲁迅先生的墨迹，去聆听那一声声的呐喊，伴着遥远的绝响，去搜寻文明的碎片，我在一篇篇的饱含思想的文章里，读懂了余秋雨的惆怅；在一个个曲折的情节中，读懂了张爱玲的痛苦；在一幅幅诗意的画面中，读懂了池莉的清新，在那里，我遇到了给予我生命力量的史铁生，衰败的地坛之中透露的那些新绿，不仅激起了他生存的希望，也给了我信心与信念；在那里我遇到了驰骋球场的贝克汉姆，顽强的拼搏不仅让他学起了一片属于自己的蓝天，也教会了我奋斗，让我不再是一个对着霞光幻想的人。");
        data.put("imageDiagnosis", "我从小就喜欢读书。为了不被妈妈发现，所以常常是躲在被窝里看书。后来，我走进了一座座金碧辉煌的宫殿，我畅游了让人心生遐想的海底世界和苍茫无际的宇宙空间。随着年龄的增大，我便顺着鲁迅先生的墨迹，去聆听那一声声的呐喊，伴着遥远的绝响，去搜寻文明的碎片，我在一篇篇的饱含思想的文章里，读懂了余秋雨的惆怅；在一个个曲折的情节中，读懂了张爱玲的痛苦；在一幅幅诗意的画面中，读懂了池莉的清新，在那里，我遇到了给予我生命力量的史铁生，衰败的地坛之中透露的那些新绿，不仅激起了他生存的希望，也给了我信心与信念；在那里我遇到了驰骋球场的贝克汉姆，顽强的拼搏不仅让他学起了一片属于自己的蓝天，也教会了我奋斗，让我不再是一个对着霞光幻想的人。");
        data.put("writeDoctorSign", "李四");
        data.put("auditDoctorSign", "李四");
        data.put("signature", "王五");
        data.put("auditTime", "2024-12-12");

        OutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        try {
            // 获取文件的输出流
            os = response.getOutputStream();
            // 读取pdf模板
            reader = new PdfReader(inputFileName);
            // 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 给表单添加中文字体
//            BaseFont bf = BaseFont.createFont("Font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);

            // 6遍历data赋值到form
            for (String key : data.keySet()) {
                form.setField(key, data.get(key).toString());
            }
            ps.setFormFlattening(true);
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("影像报告.pdf", "UTF-8"));
            log.info("======================= 生成pdf成功 =======================");
        } catch (Exception e) {
            log.error("生成pdf失败:{}", e);
        } finally {
            try {
                ps.close();
                reader.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("/download2")
    public ResponseEntity<byte[]> downloadPdf2(HttpServletResponse response) {
        String resourcePath = getClass().getClassLoader().getResource("/").getPath();
        String inputFileName = resourcePath + "pdf_template/img_template.pdf";
        String imgPath = resourcePath + "static/img/img.png";
        String fontPath = resourcePath + "pdf_template/Font/HEITI_GBK.TTF";

        // 填充表单的数据(文本)
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "王多余");
        data.put("birthday", "2001-12-23");
        data.put("sex", "男");
        data.put("address", "北京市朝阳区和平路101号");
        data.put("phone", "13600000000");

        // 填充表单的数据(图片)
        Map<String, String> picData = new HashMap<>();
        picData.put("pic", imgPath);

//        ByteArrayOutputStream os = null;

        PdfStamper ps = null;
        PdfReader reader = null;
        OutputStream os = null;
        ByteArrayOutputStream bos = null;
        try {
            os = response.getOutputStream();
            // 读取pdf模板
            reader = new PdfReader(inputFileName);
            // 根据表单生成一个新的pdf
            ps = new PdfStamper(reader, os);
            // 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 给表单添加中文字体
//            BaseFont bf = BaseFont.createFont("Font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);

            // 遍历data赋值到form
            for (String key : data.keySet()) {
                form.setField(key, data.get(key).toString());
            }
            ps.setFormFlattening(true);

            // 7 填充图片到form
            PdfStamper stamper = ps;
            picData.forEach((fieldName, imgSrc) -> {
                try {
                    List<AcroFields.FieldPosition> fieldPositions = form.getFieldPositions(fieldName);
                    for (AcroFields.FieldPosition fieldPosition : fieldPositions) {
                        // 通过域名获取所在页和坐标，左下角为起点
                        int pageno = fieldPosition.page;
                        Rectangle signrect = fieldPosition.position;
                        float x = signrect.getLeft();
                        float width = signrect.getWidth();
                        float y = signrect.getBottom();
                        // 读图片
                        Image image = Image.getInstance(imgSrc);
                        float img_width = image.getWidth();
                        // 获取操作的页面
                        PdfContentByte under = stamper.getOverContent(pageno);
                        // 图片的大小
                        image.scaleToFit(signrect.getWidth(), signrect.getHeight());
                        // 添加图片
                        image.setAbsolutePosition(x, y);
                        under.addImage(image);
                    }
                } catch (Exception e) {
                    log.error("生成pdf失败:{}", e);
                }
            });
            bos = new ByteArrayOutputStream();
            bos.writeTo(os);
            byte[] bytes = bos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode("demo2.pdf", "UTF-8"));
            HttpStatus statusCode = HttpStatus.OK;

            ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bytes, headers, statusCode);
            log.info("======================= 生成pdf成功 =======================");
            return entity;
        } catch (Exception e) {
            log.error("生成pdf失败:{}", e);
            return null;
        } finally {
            try {
                ps.close();
                reader.close();
                os.close();
                bos.close();
            } catch (Exception e) {
                log.error("生成pdf失败:{}", e);
            }
        }
    }

}
