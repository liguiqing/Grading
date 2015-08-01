<#macro meta title="" autofit=true>
  <#if autofit=true>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <#else>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  </#if>
    <link href="${request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/font-awesome.min.css" />
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/bootstrap-datetimepicker.css"/>
    
    <link rel="stylesheet" href="${request.contextPath}/static/css/grading.css"/>
    
    <!--[if IE 7]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!--[if lt IE 9]>
    <script src="${request.contextPath}/static/scripts/lib/bootstrap/html5shiv.js"></script>
    <script src="${request.contextPath}/static/scripts/lib/bootstrap/respond.min.js"></script>
    <![endif]-->
    <!--[if IE 8]>
    <style>
	.lbl {
	    display :none!important;
	}
	span.lbl{
	    display:inline-block !important;
	    position:absolute;
	    left:-20px;
	    top:-2px;	    
	}
	input[type=checkbox].ace,input[type=radio].ace {
		-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(opacity: 100)";
		filter:alpha:opacity(90);
	}
    </style>
    <![endif]-->
</#macro>

