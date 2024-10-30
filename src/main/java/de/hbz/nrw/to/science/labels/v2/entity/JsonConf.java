package de.hbz.nrw.to.science.labels.v2.entity;

import javax.persistence.*;

import lombok.Data;

@Embeddable
@Data
public class JsonConf {
  private String name;
  private String uri;
  private String type;
  private String container;
}
