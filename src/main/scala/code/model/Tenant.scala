package code.model

import net.liftweb.mapper._
import net.liftweb.util._
import net.liftweb.common._

object Tenant extends Tenant
  with LongKeyedMetaMapper[Tenant] {

  override def dbTableName = "tenants"

}

class Tenant extends LongKeyedMapper[Tenant]
  with IdPK
  with ManyToMany
  with OneToMany[Long, Tenant] {

  def getSingleton = Tenant

  object name extends MappedString(this, 255)
  
  /** Returns the User that owns this Tenant - One User can own Many Tenants */
  object owner extends MappedLongForeignKey(this, User)
  
  /** returns list of users directly assigned this Tenant */
  object users extends MappedManyToMany(UserTenant,
    UserTenant.tenant, UserTenant.user, User)

}