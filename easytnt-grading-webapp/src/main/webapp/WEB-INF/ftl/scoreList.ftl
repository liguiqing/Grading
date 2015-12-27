<!DOCTYPE html>
<title>学生成绩分析报告－${(totalPaper.examinee.name)!}</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta name="renderer" content="webkit">
<link href="../../css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../../css/bootstrap/font-awesome.min.css">
<link rel="stylesheet" href="../../css/main.css">
<link rel="stylesheet" href="../../css/scorelist.css">
<!--[if IE ]>
<link rel="stylesheet" href="../../css/ie.min.css"/>
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" href="../../css/bootstrap/font-awesome-ie7.min.css"/>
<![endif]-->
<!--[if lt IE 9]>
<script src="../../script/lib/bootstrap/html5shiv.js"></script>
<script src="../../script/lib/bootstrap/respond.min.js"></script>
<![endif]-->
</head>
<body entry="scoreList">
  <div class="container">
    <article class="row">
      <h2 class="scorelist-title">${exam.desc.name} 成绩单</h2>
      <section class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
        <article>
          <section class="row">
            <div class="col-xs-12 col-sm-6 col-md-3"><span>考生总数： </span><strong >${totalScoreList?size}</strong>人</div>
          </section>
          <section class="row">
	        <table class="table table-striped table-hover">
	          <thead>
	            <tr>
	              <td>学生姓名</td><td>学号</td><#list totalScoreList as totalPaper><#list totalPaper.paperScores as paper><td>${paper.paperName}</td></#list><td>总分</td><#break></#list>
	            </tr>
	          </thead>
	          <tbody>
	          <#list totalScoreList as totalPaper>
	            <tr>
	              <td>${(totalPaper.examinee.name)!}</td><td><a target="_blank" href="${totalPaper.examinee.uuid?c}.html">${totalPaper.examinee.uuid?c}</a></td><#list totalPaper.paperScores as paper><td>${paper.score}</td></#list><td>${totalPaper.score}</td>
	            </tr>
	          </#list>  
	          </tbody>
	        </table>
	      </section>
	    </article>    
      </section>
    </article>  
  </div>
</body>
</html>