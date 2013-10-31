package models


case class Stakeholders(stakeholder: Seq[models.StakeholderType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  modelVersion: String,
  attributes: Map[String, scalaxb.DataRecord[Any]])
