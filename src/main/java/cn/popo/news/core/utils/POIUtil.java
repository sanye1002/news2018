package cn.popo.news.core.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-04-17 下午 6:43
 * @Description description
 */
public class POIUtil {
    /**
     * 返回一个string
     * @param cell
     * @return
     */
    public static String getValue( Cell cell){

        if (cell==null){
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    /**
     * 返回一个string类型的数字类型
     * @param cell
     * @return
     */
    public static String getStringNum( Cell cell){

        if (cell==null){
            return "";
        }
        return new DecimalFormat("#").format(cell.getNumericCellValue());
    }

    /**
     * 返回一个BigDecimal类型数据
     * @param cell
     * @return
     */
    public static BigDecimal getBigDecimal(Cell cell){
        if (cell==null){
            return new BigDecimal(0);
        }
        return new BigDecimal(new DecimalFormat("#.00").format(cell.getNumericCellValue()));
    }
    public static Integer getIntNum(Cell cell){
        if (cell==null){
            return 0;
        }
        return Integer.valueOf(new DecimalFormat("#").format(cell.getNumericCellValue()));
    }
}
