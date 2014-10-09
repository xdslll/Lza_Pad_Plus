package com.lza.pad.ui.widget;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */

import com.lza.pad.core.db.model.NavigationInfo;

/**
 * 书架控件涉及的尺寸和参数
 */
public class BookSize {
    /**
     * 书架控件的宽度
     */
    public int containerWidth;

    /**
     * 书架控件的高度
     */
    public int containerHeight;

    /**
     * 共几排书架
     */
    public int rowNumber;

    /**
     * 每排书架有几本书
     */
    public int columnNumber;

    /**
     * 每排书架的宽度
     */
    public int itemWidth;

    /**
     * 每排书架的高度
     */
    public int itemHeight;

    /**
     * 书架图片的宽度
     */
    public int imgWidth;

    /**
     * 书架图片的高度
     */
    public int imgHeight;

    /**
     * 当前操作的是第几排书架
     */
    public int rowIndex;

    /**
     * 书架图片的水平间距
     */
    public int imgHorizontalSpacing;

    /**
     * 书架图片的垂直间距
     */
    public int imgBottomSpacing;

    public BookSize(int containerWidth, int containerHeight, NavigationInfo ni) {
        this(containerWidth, containerHeight, ni.getDataRowNumber(), ni.getDataColumnNumber());
    }

    public BookSize(int containerWidth, int containerHeight, int row, int column) {
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
        this.rowNumber = row;
        this.columnNumber = column;
    }
}
