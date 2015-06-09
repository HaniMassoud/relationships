package code.model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._


object UserTenant extends UserTenant
  with LongKeyedMetaMapper[UserTenant] {
  
  override def dbTableName = "usertenants"
  
  // create a composite key for UserTenant
  override def dbIndexes = Index(user, tenant) :: super.dbIndexes  
  
  
}


class UserTenant extends LongKeyedMapper[UserTenant]
  with IdPK {
  
  def getSingleton = UserTenant

  object user extends MappedLongForeignKey(this, User) 
  
  object tenant extends MappedLongForeignKey(this, Tenant)
  
}