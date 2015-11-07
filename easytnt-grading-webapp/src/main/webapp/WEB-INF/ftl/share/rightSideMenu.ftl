<#if rightSideMenu?exists>
  <ul class="sidebar-menu">
  <#list rightSideMenu as menu>
    <li <#if menu.actived=true>class="actived"</#if>><a href="${request.contextPath}/${menu.url}"><b>${menu.name}</b></a></li>
  </#list>
  </ul>
</#if>