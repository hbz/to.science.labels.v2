package de.hbz.nrw.to.science.labels.v2.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "labels")
@Data
@NoArgsConstructor
public class Label {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(name = "group",length = 128)
  private String group;

  @Column(name = "label",length = 128)
  private String labelStr;

  @Embedded
  private JsonConf jsonConf;

  @Column
  private String comment;

}
