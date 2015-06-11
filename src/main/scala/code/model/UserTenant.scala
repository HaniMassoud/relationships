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

  object user extends MappedLongForeignKey(this, User) {
    override def validSelectValues =
      Full(validUserValues)
  }
  
  object tenant extends MappedLongForeignKey(this, Tenant) {
    override def validSelectValues = 
      Full(validTenantValues)
  }
  
  //========================================================================
  //
  // Helper functions to retrieve the valid list of users
  //
  //========================================================================
  
  def validUserValues: List[(Long, String)] = {
    User.currentUser match {
      case Full(c: User) => c.administeredUsers.map( u => (u.id.get, u.email.get) )
      case _ => Nil // unable to retrieve user details
    }
  }
  
  //========================================================================
  //
  // Helper functions to retrieve the valid list of tenants
  //
  //========================================================================
  
  def validTenantValues: List[(Long, String)] = {
    User.currentUser match {
      case Full(c: User) => c.administeredTenants.map( t => (t.id.get, t.name.get) )
      case _ => Nil // unable to retrieve user details
    }
  }
  
}