package bootstrap.liftweb

import code.model._

object CreateTestData {

  def all = {
  
    // Create test users
    val user1 = createUser("user1")
    val user2 = createUser("user2")
    val user3 = createUser("user3")
    val user4 = createUser("user4")
    val user5 = createUser("user5")
    val user6 = createUser("user6")
    val user7 = createUser("user7")
    val user8 = createUser("user8")
    val user9 = createUser("user9")
    val user10 = createUser("user10")
    val user11 = createUser("user11")
    val user12 = createUser("user12")
        
    // Create test tenants
    val tenant1 = createTenant("tenant1", user1)
    val tenant2 = createTenant("tenant2", user2)
    val tenant3 = createTenant("tenant3", user3)
    val tenant4 = createTenant("tenant4", user4)
    val tenant5 = createTenant("tenant5", user5)
    val tenant6 = createTenant("tenant6", user6)
    
    // Assign users to Tenant1
    tenant1.users += user7
    tenant1.users += user8
    tenant1.users += user9
    tenant1.users += user10
    tenant1.users += user11
    tenant1.users += user12
    tenant1.save()
    
  }
  
  def createUser(name:String) =
    User.create
      .firstName(name)
      .lastName(name)
      .email(name + "@nosuchemail.com")
      .password("password")
      .validated(true)
      .superUser(false)
      .saveMe

  def createTenant(name: String, owner: User) = 
      Tenant.create
      .name(name)
      .owner(owner)
      .saveMe

}

