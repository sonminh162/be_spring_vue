package com.rabiloo.custom.dto;

import com.rabiloo.custom.collect.Feature;
import com.rabiloo.custom.collect.FileEntity;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
public class AppNewsDto implements Serializable {

    private String title;
    private String subTitle;
    private FileEntity mainImage;
    private Date startTime;
    private Date endTime;
    private String body;
    private Feature touchFeatureApp;

}
