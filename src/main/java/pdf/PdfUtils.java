package pdf;

import bean.DataEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import form.Start;
import utils.DataColumnsUtils;
import utils.JsonRead;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class PdfUtils {

    private static final int TBL_COLUMNS_LEN = 9 ;

    /**
     *
     * @param str 字符串
     * @param font 字体
     * @param high 表格高度
     * @Param alignCenter 是否水平居中
     * @Param alignMidde  是否垂直居中
     * @Param haveColor 是否有背景色(灰色)
     * @return
     */
    private static PdfPCell  mircoSoftFont(String str,Font font,int high,boolean alignCenter,boolean alignMidde,boolean haveColor){

        PdfPCell pdfPCell  = new PdfPCell(new Phrase(str,font));

        pdfPCell.setBorderColor(new BaseColor(220, 220, 220));
        pdfPCell.setMinimumHeight(high);
        pdfPCell.setFixedHeight(high);
        pdfPCell.setUseAscender(true); // 设置可以居中
        if (alignCenter){
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); // 设置水平居中
        }
        if (alignMidde){
            pdfPCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 设置垂直居中
        }
        if (haveColor){
            //颜色代码 RGB
            pdfPCell.setBackgroundColor(new BaseColor(217,217,217));
        }
        return pdfPCell;
    }




    public static void createHardwarePDF(String outputPath, java.util.List<DataEntity> datas)throws Exception {
        //新建文档对象，页大小为A4纸，然后设置4个边距
        //如果需要页码，须和PDFBuilder保持一致
        Document document = new Document(PageSize.A4, 20, 20, 30, 30);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));


        writer.setPageEvent(new PDFBuilder("",12,PageSize.A4));

        document.open();
       // addImageToPdf(document);
        //创建字体
        String fontttf = "./resources/font/";
        fontttf = fontttf + JsonRead.getInstance().getJsonTarget("pdf-font");
        //BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        BaseFont baseFont = BaseFont.createFont(fontttf,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

        //字体对象
        Font size14font = new Font(baseFont, 14, Font.NORMAL);  //大小为14的正常字体
        Font size12font = new Font(baseFont, 12, Font.NORMAL);  //大小为14的正常字体
        Font size10font = new Font(baseFont, 10, Font.BOLD); //大小为10的粗体
        Font size8font = new Font(baseFont, 8, Font.NORMAL); //大小为10的粗体



        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        PdfPTable firstRowContent = new PdfPTable(3);
        firstRowContent.setWidthPercentage(90);
        firstRowContent.setWidths(new int[]{50,50,50});


        /*
        image.setAlignment(Image.ALIGN_CENTER);
        image.scalePercent(40); //依照比例缩放
        image.setAbsolutePosition(40,60)
         //img.scaleAbsolute(10,5);
       // img.scalePercent(1); //依照比例缩放
        //img.setWidthPercentage(90);
        */
        //firstRowContent.addCell(img);
        String deviceId = Start.getInstance().resultDeviceId.replaceAll("at\\+deviceid=","");
        String rDeviceId = deviceId;
        if (Start.getInstance().tabbedPane1.getSelectedIndex() == 1)
        {
            rDeviceId = Start.getInstance().comboBoxDeviceId.getSelectedItem().toString();
        }
        firstRowContent.addCell(mircoSoftFont("Created on :" + sdf.format(new Date()) +"\n" +
                        "Device id :" + rDeviceId ,
                size8font, 15, false, true,false));
//        firstRowContent.addCell(mircoSoftFont(Start.getInstance().deviceName.getText() , size12font, 30, true, true,false));
        firstRowContent.addCell(mircoSoftFont("" , size12font, 30, true, true,false));
        Image img = Image.getInstance("./resources/img/logo.jpg");
        img.scalePercent(30); //依照比例缩放
        PdfPCell imgCell = new PdfPCell(img,false);
        imgCell.setBorderColor(new BaseColor(220, 220, 220));
        imgCell.setUseAscender(true); // 设置可以居中
        imgCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        imgCell.setMinimumHeight(20);
        imgCell.setFixedHeight(20);

        firstRowContent.addCell(imgCell);

        document.add(firstRowContent);
//        PdfPTable secondRowContent = new PdfPTable(1);




        //        deviceName

        int tableWidth[] = new int[]{50,50,50,50,50,80,50,50};
        //添加表头
        PdfPTable tableContent = new PdfPTable(8);

        tableContent.setWidthPercentage(90);  //表格的宽度，百分比缩放
        tableContent.setWidths(tableWidth);
        //tableContent.setTotalWidth(new float[]{0.3f, 0.2f, 0.2f,0.2f,0.2f,0.4f,0.4f,0.4f});
        tableContent.addCell(mircoSoftFont(" Treatent", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Date", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Time", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Volume", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Duration", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Operator Name", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Room", size12font, 20, true, true, true));
        tableContent.addCell(mircoSoftFont(" Content", size12font, 20, true, true, true));
        //document.add(tableHead);

        //添加表明细

        for(int i = 0 ; i < datas.size() ; i++){
            for (int col = 0; col < TBL_COLUMNS_LEN; col++) {
                DataEntity value = datas.get(i);
                //导出时添加序列号
//                value.setsTreatent(String.valueOf(i+1));
                switch (col) {
                    case DataColumnsUtils.COL_TREATENT:
                        tableContent.addCell(mircoSoftFont(value.getsTreatent() , size8font, 20, true, true, false));
                        break;
                    case DataColumnsUtils.COL_DATE:
                        tableContent.addCell(mircoSoftFont(value.getsDate(), size8font, 20, true, true, false));
                        break;
                    case DataColumnsUtils.COL_TIME:
                        tableContent.addCell(mircoSoftFont(value.getsTime(), size8font, 20, true, true, false));
                        break;
                    case DataColumnsUtils.COL_VOLUME:
                        tableContent.addCell(mircoSoftFont(value.getsVolume(), size8font, 20, true, true, false));
                        break;
                    case DataColumnsUtils.COL_DURATION:
                        tableContent.addCell(mircoSoftFont(value.getsDuration(), size8font, 20, true, true, false));

                        break;
                    case DataColumnsUtils.COL_OPERATORNAME:
                        tableContent.addCell(mircoSoftFont(value.getsOperatorName(), size8font, 20, true, true, false));

                        break;
                    case DataColumnsUtils.COL_ROOM:
                        tableContent.addCell(mircoSoftFont(value.getsRoom(), size8font, 20, true, true, false));
                        break;
                    case DataColumnsUtils.COL_CONTENT:
                        tableContent.addCell(mircoSoftFont(value.getsContent(), size8font, 20, true, true, false));
                        break;
                }
            }
        }
        document.add(tableContent);

        //添加水印和页码


        //onEndPage(writer,document);

        document.close();
        writer.close();
    }


    public static void addImageToPdf(Document doc)
    {

        String imagePath = "./resources/img/logo.jpg";
        //String imagePath = "src/main/resources/logo.jpg";
        try{
            //doc.newPage();

            Image png1 = Image.getInstance(imagePath);
            //Image png1   = new ImageIcon("./resources/img/logo.jpg").getImage();
            float height = png1.getHeight();
            float width = png1.getWidth();
            int percent = getPercent2(height, width);
            png1.setAlignment(Image.MIDDLE);
            png1.setAlignment(Image.TEXTWRAP);
            png1.scalePercent(percent + 3);
            doc.add(png1);
        }catch(Exception e){}



    }
    private static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }
/*
    public static void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = new Rectangle(0, 38, 50, 50);

        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_BOTTOM,
                new Phrase(String.format("- %d -", writer.getPageNumber())), (rect.getLeft() + rect.getRight()) / 2,
                rect.getBottom() - 18, 0);
    }

 */
}

