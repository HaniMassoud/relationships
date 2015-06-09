package code.snippet

import net.liftweb._
import util._
import Helpers._
import http._
import sitemap._
import Loc._
import net.liftweb.common.{ Box, Full, Empty, Failure, ParamFailure }
import scala.xml.NodeSeq
import net.liftweb.mapper._
import net.liftweb.mapper.{ By, OrderBy, MaxRows, Like, Cmp, NotNullRef, NullRef, QueryParam, StartAt, Ascending, Descending }
import net.liftweb.mapper.view.{ MapperPaginatorSnippet, SortedMapperPaginatorSnippet }
import code.model._

import net.liftweb.http.SHtml.{text, ajaxSubmit, ajaxInvoke, textarea}
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.http.js.JE


class OwnedTenantsShow extends PaginatorSnippet[Tenant] {

  // get the Current User 
  val u: User = User.currentUser match {
    case Full(u) => u
    case _       => new User
  }
  
  override def itemsPerPage = 3
  override def count = u.ownedTenants.length
  override def page = u.ownedTenants.slice(curPage*itemsPerPage, curPage*itemsPerPage + itemsPerPage)
  
  var rowCount = count
  
  def render = showTenantsListToAdmin

  def renderTenants: CssSel =
    
    ".uTenantsCount" #> count &
    ".uTenants *" #> page.map( t => {
      
      val rowId = "tenant" + t.id.toString
      val rowViewId = rowId + "View"
      val rowEditId = rowId + "Edit"
      val rowLinkId = rowId + "Link"
      val rowInputId = rowId + "Input"
      
      "li [data-row]" #> rowId &
        ".viewMode [id]" #> rowViewId &
        ".editMode [id]" #> rowEditId &
        ".tenantLink [id]" #> rowLinkId &
        ".tenantId" #> t.id & 
        ".tenantName" #> t.name &
        ".editableTenantName" #> text(t.name.get, t.name.set _) &
        ".tenantNameSave" #> SHtml.ajaxSubmit ( 
           "Save", 
             { () => User.currentUser match {
              case Full(u) if u.ownedTenants.contains(t) => {
                // S.error("t.save() is not working - need to fix it")
                // TODO - get t.save() to work here (like it works in CreateTestData.scala)
                t.save()
                JsCmds.Run("jQuery('#" + rowLinkId + "').text('" + t.name + "')") &
                JsCmds.Run("jQuery('#" + rowViewId + "').show()") &
                JsCmds.Run("jQuery('#" + rowEditId + "').hide()")
              }
              case _ =>  {
                S.error( "You are not authorised to edit this project ..." )
              }
             }}) &
        ".tenantLink [href]" #> "#" // "/admin/tenants/show/%s".format(t.id)
    }) 

  def clearTenantsInfo =
    ".uTenantsCount" #> "" &
    ".uTenants *" #> u.ownedTenants.take(itemsPerPage).map( t =>
      "li [data-row]" #> "" &
      ".viewMode [id]" #> "" &
      ".editMode [id]" #> "" &
      ".tenantLink [id]" #> "" &
      ".tenantId" #> "" &
      ".tenantName *" #> "" &
      ".editableTenantName" #> "" &
      ".tenantNameSave" #> "" &
      ".tenantLink [href]" #> "#"
    ) 
    
  // helper function to check if the current user is valid (retrieved correctly)
  // we are not checking if the user is authorized because we are only retrieving their
  // administered entities
  def showTenantsListToAdmin = User.currentUser match {
    case Empty => {
      S.error("Oops! Unable to access current user info.")
      clearTenantsInfo
    }
    case Full(c: User) if c == u => renderTenants
    case f: Failure => {
      S.error(f.msg)
      clearTenantsInfo
    }
    case _ => {
      S.error("System error: Unable to retrieve user information.")
      clearTenantsInfo
    }
  }

}