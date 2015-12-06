<div>
<table>
<#assign examinees=query.results>
<#include "examineeRow.ftl">
</table>
<#import "/taglib/commons/pager.ftl" as page > 
<@page.pager pager=query css="margin:0;text-align:left;"/>
</div>