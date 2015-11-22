<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <title></title>	
  <meta charset="utf-8" />   
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<meta name="renderer" content="webkit">
    <link href="${request.contextPath}/static/css/bootstrap/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/font-awesome.min.css" />
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/bootstrap-datetimepicker.css"/>
	<link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/buttons/buttons.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/dialog.css"/>
    <link rel="stylesheet" href="${request.contextPath}/static/css/smartWizard/smart_wizard_vertical.css"/>
    
    
    
	<!--[if IE ]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/ie.css"/>
    <![endif]-->
	
    <!--[if IE 7]>
    <link rel="stylesheet" href="${request.contextPath}/static/css/bootstrap/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!--[if lt IE 9]>
    <script src="${request.contextPath}/static/script/lib/bootstrap/respond.min.js"></script>
    <script src="${request.contextPath}/static/script/lib/bootstrap/html5shiv.js"></script>
    <![endif]-->
</head>

<body entry="" rootPath="${request.contextPath}/" style="overflow:hidden">

<input id="test1" type="button" value="test">

</body>
 <script type="text/javascript">
	window["app"]={contextPath:'${request.contextPath}',rootPath:'${request.contextPath}/',entry:'importPaper/test'};
  </script>
<script type="text/javascript" data-main="${request.contextPath}/static/script/main"  src="${request.contextPath}/static/script/require.js"></script>
   <script type="text/javascript">
   var v = + + new Date;
	requirejs.config({
		urlArgs : "v="+v,
	});
  </script>
</html>
