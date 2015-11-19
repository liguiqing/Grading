<#macro pager id="pager" pager={"curpage":"1","totalpage":"0","pagesize":"15","totalrows":"0"} css="margin-top: 20px; text-align: center;">
<div class="pager-container">
  <div id="${id}"  class="pager" style="${css}">
    <input type="hidden" id="pageNum" value="${(pager.curPage)!1}"> 
    <input type="hidden" id="pageCount" value="${(pager.totalPage)!0}">
    <input type="hidden" id="pageSize" value="${(pager.pageSize)!10}"> 
    <input type="hidden" id="totalRows" value="${(pager.totalRows)!0}">  
  </div>
</div>
</#macro>