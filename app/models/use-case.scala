// Generated by <a href="http://scalaxb.org/">scalaxb</a>.
package models


case class Useu45caseType(annotation: Seq[models.AnnotationType] = Nil,
  description: models.DescriptionType,
  documentu45history: models.Documentu45historyType,
  useu45caseu45properties: models.Useu45caseu45propertiesType,
  mainu45flow: models.MainSequenceType,
  alternateu45flows: Option[models.Alternateu45flowsType] = None,
  exceptionu45flows: Option[models.Exceptionu45flowsType] = None,
  businessu45rules: Option[models.RequirementsCollectionType] = None,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  id: String,
  name: String,
  modelu45version: String,
  attributes: Map[String, scalaxb.DataRecord[Any]])


case class Useu45caseu45propertiesType(annotation: Seq[models.AnnotationType] = Nil,
  trigger: models.DescriptionType,
  goal: models.DescriptionType,
  primaryu45actor: Seq[models.Actoru45refType] = Nil,
  secondaryu45actor: Seq[models.Actoru45refType] = Nil,
  preu45requisites: models.DescriptionType,
  successu45outcome: models.DescriptionType,
  failureu45outcome: models.DescriptionType,
  priority: String,
  complexity: BigInt,
  taxonomy: Seq[models.TaxonomyType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]])


trait SequenceTypable {
  val annotation: Seq[models.AnnotationType]
  val step: Seq[models.StepType]
  val any: Seq[scalaxb.DataRecord[Any]]
  val attributes: Map[String, scalaxb.DataRecord[Any]]
}


case class SequenceType(annotation: Seq[models.AnnotationType] = Nil,
  step: Seq[models.StepType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]]) extends SequenceTypable


/** 
                Represents the structure of the main flow
            
*/
case class MainSequenceType(annotation: Seq[models.AnnotationType] = Nil,
  step: Seq[models.StepType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  end: scalaxb.DataRecord[Any],
  attributes: Map[String, scalaxb.DataRecord[Any]]) extends SequenceTypable


case class Rejoin(atu45step: String)


/** 
                We need to add additional markers on these flows such that events occuring on the main flow can 
				reference these alternate and exception flows.
            
*/
case class AlternateSequenceType(annotation: Seq[models.AnnotationType] = Nil,
  step: Seq[models.StepType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  alternatesequencetypeoption: scalaxb.DataRecord[Any],
  id: String,
  title: String,
  attributes: Map[String, scalaxb.DataRecord[Any]]) extends SequenceTypable

trait AlternateSequenceTypeOption

case class StepType(annotation: Seq[models.AnnotationType] = Nil,
  description: models.DescriptionType,
  proposedu45release: Option[models.ReleaseRefType] = None,
  refu45alternateu45flow: Seq[models.Refu45alternateu45flowsType] = Nil,
  refu45exceptionu45flow: Seq[models.Refu45alternateu45flowsType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  stepu45id: String,
  attributes: Map[String, scalaxb.DataRecord[Any]])


case class Alternateu45flowsType(alternateu45flow: Seq[models.AlternateSequenceType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]])


case class Refu45alternateu45flowsType(refId: String,
  condition: String)


case class Exceptionu45flowsType(exceptionu45flow: Seq[models.AlternateSequenceType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]])
