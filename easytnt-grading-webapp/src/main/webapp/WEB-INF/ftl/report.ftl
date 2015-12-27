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
<link rel="stylesheet" href="../../css/report.css">
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
<body entry="report">
  <div id="report">
    <nav class="navbar navbar-default navbar-fixed-top" style="display:none;">
      <div class="container" >
        <div class="navbar-header row" >
          <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 user-info"><strong>${(totalPaper.examinee.name)!}</strong><span>同学</span></div>
          <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
          <button type="button" class="navbar-toggle " data-toggle="collapse" 
            data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only" >Toggle navigation</span>
            <span class="icon-bar" ></span>
            <span class="icon-bar" ></span>
            <span class="icon-bar" ></span>
          </button>
          </div>
        </div>
        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" >
          <div class="navbar-right" >
            <form method="post" action="/login" class="navbar-form" novalidate="" >
              <div class="form-group" >
                <input type="text" name="username" placeholder="账号" class="form-control" >
              </div>
              <div class="form-group" >
                <input type="password" name="password" class="form-control" placeholder="密码" >
              </div>
              <button type="submit" class="btn btn-primary" >登录</button>
            </form>
          </div>
        </div>
      </div>
    </nav>
    <div class="jumbotron home" >
      <h2 class="report-title">${totalPaper.examName} 成绩分析</h2>
      <div class="container" >     
        <div class="text-wrapper" >
          <div class="row" >  
            <div class="col-xs-12 col-sm-6 col-md-3"><span>姓名： </span><strong >${(totalPaper.examinee.name)!}</strong></div>
            <div class="col-xs-12 col-sm-6 col-md-3"><span>唯一编号： </span><strong >${(totalPaper.examinee.uuid?c)!}</strong></div>  
            <div class="col-xs-12 col-sm-6 col-md-3"><span>学校： </span><strong >${(totalPaper.examinee.school)!}</strong></div>
            <div class="col-xs-12 col-sm-6 col-md-3"><span>班级： </span><strong >${(totalPaper.examinee.clazzName)!}</strong></div>
          </div>
          <div class="row comment">
            <div class="col-xs-12">
              <hr/>
              <pre>
<#if totalPaper.degree gt 1>恭喜</#if>您取得<strong>${totalPaper.rankingName}</strong>水平的成绩。根据心理学对于学习的规律研究，学习的每个阶段都有“天花板效应”，就是达到一个相对较高的水平之后，会稳定一段时间，经过不懈的努力才会再再上升一个大的台阶。建议您继续保持当前的学习方法和热情，不要对自己的能力有怀疑，也不要急于求成，要遵守科学的规律。
推荐您阅读：优秀课件初一历史与社会、优秀英文原著改编《书虫系列》、知名演讲TED、优秀图书推荐-历史类、优秀图书推荐-社科类、心理学小知识“学习规律”。
推荐您练习：词汇量测试、初中一年级历史与社会针对性练习
              
              </pre>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="home-panel">
    <aside></aside>
    <aside>
      <div class="container steps" >
         <article class="row">
          <h1>各科目成绩</h1>
          <div class="home-panel-heading-divider"></div>
          <section class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div class="score-desc">
              <h4>位置排行</h4>
			  <ul>
                <li class="<@getRankingCss totalPaper/>"><label>你的总分：</label><span>${totalPaper.rankingName!}</span></li>
                <li class="excellence"><label>靠前的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getRankingName paper 3/></#list></#if></li>
                <li class="good"><label>中上的科目有：</label><span><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getRankingName paper 2/></#list></#if></span></li>
                <li class="pass"><label>中下的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getRankingName paper 1/></#list></#if></li>
                <li class="terrible"><label>靠后的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getRankingName paper 0/></#list></#if></li>
              </ul>
              <h4>得分情况</h4>
              <ul>
                <li class="<@getDegreeCss totalPaper/>"><label>你的总分：</label><span >${totalPaper.degreeName}</span></li>
                <li class="excellence"><label>优秀的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getDegreeName paper 3/></#list></#if></li>
                <li class="good"><label>良好的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getDegreeName paper 2/></#list></#if></li>
                <li class="pass"><label>及格的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getDegreeName paper 1/></#list></#if></li>
                <li class="terrible"><label>不及格的科目有：</label><#if totalPaper.paperScores??><#list totalPaper.paperScores as paper><@getDegreeName paper 0/></#list></#if></li>
              </ul>              
              <p style="margin-top:15px;">
说明：优等分数线（Q1）为排名的25%，星级为<i class="glyphicon glyphicon-star"></i>；中上分数线（Q2）为排名的50%，星级为<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>；靠前分数线（Q3）为排名的75%，星级为<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>；靠后评定为<i class="glyphicon glyphicon-star-empty"></i>。
              </p>
              <p>及格分数线为该科目满分的60%，星级为<i class="glyphicon glyphicon-star"></i>；良好分数线为该科目满分的75%，星级为<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>；优秀分数线为该科目满分的90%，星级为<i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i><i class="glyphicon glyphicon-star"></i>；不及格科目评定为<i class="glyphicon glyphicon-star-empty"></i>。</p>               
			  <p>排位为百分位：自己跟其他同学相比较的排名转换为百分制，数值越小则排名越靠前。</p>                             
              <div class="clearfix"></div>            
            </div>
          </section>
          <section class="col-xs-12 col-sm-12 col-md-12 col-lg-12"  style="margin-bottom:15px;">
            <article class="col-xs-12 col-sm-6 col-md-4 col-lg-4 ">
              <section class="row subject">
                <h1>总分</h1>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 glyphicon-wrapper" ><div class="glyphicon <@getLevelIconCss totalPaper/>" ></div></div>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 score-info">
                  <div class="col-xs-6"><label>得分星级:</label><span><@getStarIconCss totalPaper.degree/></span></div>
                  <div class="col-xs-6"><label>个人得分:</label><span>${totalPaper.score}</span></div>
                  <div class="col-xs-6"><label>排位星级:</label><span><@getStarIconCss totalPaper.ranking/></span></div>
                  <div class="col-xs-6"><label>个人排位:</label><span>${(totalPaper.rankingRate * 100)?string("#.##")}%</span></div>
                  <div class="col-xs-6"><label>科目满分:</label><span>${totalPaper.fullScore}</span></div>
                  <div class="col-xs-6"><label>得分率:</label><span>${(totalPaper.scoreRate * 100)?string("#.##")}%</span></div>
                </div>
              </section>  
            </article>          
            <#if totalPaper.paperScores??><#list totalPaper.paperScores as paper>
            <article class="col-xs-12 col-sm-6 col-md-4 col-lg-4 ">
              <section class="row subject">
                <h1>${paper.paperName}</h1>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 glyphicon-wrapper" ><div class="glyphicon <@getLevelIconCss paper/>" ></div></div>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 score-info">
                  <div class="col-xs-6"><label>得分星级:</label><span><@getStarIconCss paper.degree/></span></div>
                  <div class="col-xs-6"><label>个人得分:</label><span>${paper.score}</span></div>
                  <div class="col-xs-6"><label>排位星级:</label><span><@getStarIconCss paper.ranking/></span></div>
                  <div class="col-xs-6"><label>个人排位:</label><span>${(paper.rankingRate * 100)?string("#.##")}%</span></div>
                  <div class="col-xs-6"><label>科目满分:</label><span>${paper.fullScore}</span></div>
                  <div class="col-xs-6"><label>得分率:</label><span>${(paper.scoreRate * 100)?string("#.##")}%</span></div>
                </div>
              </section>  
            </article>
            </#list></#if>                                                                
          </section>         
          <#if totalPaper.paperScores??><#list totalPaper.paperScores as paper> 
          <section class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
            <article class="score-detail">
              <section class="row subject">
                <h1>${paper.paperName}学科</h1>
                <h3>得分：${paper.score}</h3>
                <#assign items = paper.itemsScore>
                <#assign rowIndex = 0>
                <#assign curItem = 0>
                <#assign rowsInPanel = ((items?size)/3)?ceiling>
                <#list 0..2 as panel>                
                <ul class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                  <li class="col-xs-12">题号(选项)>>>得分</li>
                  <#list items as item>                                       
                    <#if rowIndex lt rowsInPanel> 
                      <#if item_index gte curItem>
                      <#assign curItem = curItem+1>                                  
                      <li class="col-xs-12 <#if item.score=0>terrible</#if>"><span style="margin-right:10px;">${item.itemName!}<#if item.object>(${item.answer!})<#else>${item.myAnswer!}</#if></span>>>><span style="margin-left:10px;">${item.score!}</span><#if item.object><span style="margin-left:5px">(${item.myAnswer!})</span></#if></li>
                      <#assign rowIndex =rowIndex+1>
                      </#if>                                         
                    </#if>
                  </#list>
                  <#assign rowIndex =0>
                </ul>
                </#list>           
                <div class="clearfix"></div>                                
              </section>              
            </article>
          </section>
          </#list></#if>
          <section class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <pre class="score-comment">
本人答题卡批改原图、知识掌握、能力达成、错题分析与巩固练习等内容请查看详细版报告。
            </pre>
          </section>                                          
        </article>
      </div>
    </aside>
    </div>
    <footer>
    </footer>
  </div>
</body>

<script type="text/javascript" data-main="../../script/main" src="../../script/require.js"></script>
<script type="text/javascript">
   var v = + + new Date;
	requirejs.config({
		urlArgs : "v="+v
	});
</script>
</html>
<#macro getRankingCss paperScore>
  <#switch paperScore.ranking>
  <#case 3>
    excellence
    <#break>
  <#case 2>
    good
    <#break>
  <#case 1>
    pass
    <#break>
  <#default>
    terrible           
  </#switch>
</#macro>

<#macro getDegreeCss paperScore>
  <#switch paperScore.ranking>
  <#case 3>
    excellence
    <#break>
  <#case 2>
    good
    <#break>
  <#case 1>
    pass
    <#break>
  <#default>
    terrible           
  </#switch>
</#macro>

<#macro getLevelIconCss paperScore>
  <#switch paperScore.ranking>
  <#case 3>
    glyphicon-education
    <#break>
  <#case 2>
    glyphicon-thumbs-up
    <#break>
  <#case 1>
    glyphicon-edit
    <#break>
  <#default>
    glyphicon-wrench           
  </#switch>
</#macro>

<#macro getStarIconCss value=0>
  <#if value=0><i class="glyphicon  glyphicon-star-empty"></i>
  <#else>  
    <#list 1..value as x>
     <i class="glyphicon  glyphicon-star"></i>
    </#list> 
  </#if>
</#macro>


<#macro getRankingName paperScore ranking=0>
  <#if paperScore.ranking=ranking><span>${paperScore.paper.name}</span></#if>
</#macro>

<#macro getDegreeName paperScore degree=0>
  <#if paperScore.degree=degree><span>${paperScore.paper.name}</span></#if>
</#macro>