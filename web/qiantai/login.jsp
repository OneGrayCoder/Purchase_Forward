﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/qiantai/css/style.css" rel="stylesheet" type="text/css" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>用户登录</title>
	<script>
		function turnLogin(){
			window.location.href = "${pageContext.request.contextPath}/qiantai/register.jsp";
		}

        function changeImg(){
		    //用来向服务器端发送请求
			//指向图片，再次发送请求
			document.getElementById("img1").src = "${pageContext.request.contextPath}/qiantai/UserServlet?reqType=securityCodes&date="+new Date().getMilliseconds();
		}
	</script>
</head>
<body>
<!--LOGO欢迎信息和登陆注册功能-->
<div class="block clearfix">
	<div class="f_l">
		<a href="index.php.htm" name="top"><img src="images/logo.gif"/></a>
	</div>
 	<div class="f_r log">
		<ul>
	    	<li class="userInfo">
  				<font id="ECS_MEMBERZONE">
					<div id="append_parent"></div>
 					欢迎光临本店&nbsp;&nbsp;&nbsp;&nbsp;
 					<a href="login.jsp"><img src="images/bnt_log.gif"/></a>
 					<a href="register.jsp"><img src="images/bnt_reg.gif"/></a>
 				</font>
   			</li>
      	</ul>
 	</div>
</div>
<div  class="blank"></div>

<!--顶层功能导航栏-->
<div id="mainNav" class="clearfix">
	<a href="IndexServlet" class="cur">首页<span></span></a>
	<a href="###">买家必看<span></span></a>
	<a href="###">优惠活动<span></span></a>
	<a href="flow.jsp">查看购物车<span></span></a>
	<a href="###">报价单<span></span></a>
	<a href="###">留言板<span></span></a>
	<a href="###">团购商品<span></span></a>
</div>
<div class="block">
	<div class="box">
		<div class="helpTitBg clearfix"></div>
	</div>
</div>
<div class="blank"></div>
<div class="usBox clearfix">
	<div class="usBox_1 f_l">
		<div class="logtitle"></div>
		<form name="formLogin" action="${pageContext.request.contextPath}/qiantai/UserServlet?reqType=Login" method="post">
			<table width="100%" border="0" align="left" cellpadding="3" cellspacing="5">
				<tr>
					<td width="20%" align="right">用户名</td>
					<td width="80%"><input name="username" type="text" size="25" class="inputBg" /></td>
				</tr>
				<tr>
					<td align="right">密码</td>
					<td><input name="password" type="password" size="25"  class="inputBg"/></td>
				</tr>
				<tr>
					<td align="right">验证码</td>
					<td>
						<input type="text" name="securityCoder" id="vertification" class="inputBg" />
						<img id="img1"src="${pageContext.request.contextPath}/qiantai/SecurityCode?reqType=securityCodes"  width="90" height="20"/><a id="securityCode" href="javascript:changeImg()" >看不清,下一张</a></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left">
						<input type="submit" name="submit" value="" class="us_Submit" />
					</td>
					${sessionScope.loginInfo}
				</tr>
			</table>
		</form>
	</div>
	<div class="usTxt">
		<strong>如果您不是会员，请注册</strong>  <br />
		<strong class="f4">友情提示：</strong><br />
		不注册为会员也可在本店购买商品<br />
		但注册之后您可以：<br />
		1. 保存您的个人资料<br />
		2. 收藏您关注的商品<br />
		3. 享受会员积分制度<br />
		4. 订阅本店商品信息  <br />
		<a href="###"><img src="images/bnt_ur_reg.gif" onclick="turnLogin()" /></a>
	</div>
</div>
<div class="blank"></div>
<!--友情连接区-->
<div id="bottomNav" class="box">
	<div class="box_1">
		<div class="links clearfix">
			[<a href="###" target="_blank" title="友情连接1">友情连接1</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接2</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接3</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接4</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接5</a>]
		</div>
	</div>
</div>
<div class="blank"></div>

<!--底层导航栏-->
<div id="bottomNav" class="box">
	<div class="box_1">
		<div class="bNavList clearfix">
   			<div class="f_l"></div>
   			<div class="f_r">
				<a href="#top"><img src="images/bnt_top.gif" /></a> 
				<a href="###"><img src="images/bnt_home.gif"/></a>
   			</div>
  		</div>
 	</div>
</div>
<div class="blank"></div>

<!--版权信息栏-->
<div class="text"align="center">
 &copy; 2010-2015 网上商城 版权所有，并保留所有权利。<br />
          E-mail: 123456@qq.com<br />
    ICP备案证书号:<a href="###" target="_blank">粤ICP备1234568</a><br />
          <div align="center"  id="rss"><a href="###"><img src="images/xml_rss2.gif"alt="rss" /></a></div>
</div>
</body>

</html>
