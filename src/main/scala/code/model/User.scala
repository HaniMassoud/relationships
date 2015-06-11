package code
package model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  // define the order fields will appear in forms and output
  override def fieldOrder = List(id, firstName, lastName, email,
  locale, timezone, password, textArea)

  // comment this line out to require email validations
  override def skipEmailValidation = true
}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends MegaProtoUser[User] 
  with ManyToMany
  with OneToMany[Long, User] {
  
  def getSingleton = User // what's the "meta" server

  // define an additional field for a personal essay
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Personal Essay"
  }
  
  /** returns the list of tenants for which the user is an authorized admin */
  def administeredTenants: List[Tenant] = {
    
    Tenant.findAll
        
  }
  
  /** returns the list of users for which this user is an authorized admin */
  def administeredUsers: List[User] ={
    
    User.findAll
    
  }
  
  /** returns list of tenants that this user is assigned to */
  object assignedToTenants extends MappedManyToMany(UserTenant,
    UserTenant.user, UserTenant.tenant, Tenant)
  
  /** returns list of tenants that this user owns */
  object ownedTenants extends MappedOneToMany(Tenant, Tenant.owner, OrderBy(Tenant.id, Ascending))
  
  /** Returns the Tenant that this User has selected to currently work with - One Tenant can be selected by Many Users */
  object selectedTenant extends MappedLongForeignKey(this, Tenant)
  
  
}

