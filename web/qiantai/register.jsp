<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/qiantai/css/style.css" rel="stylesheet" type="text/css"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script>
        //控制用户名不能重复
        //这里面写ajax请求
        $(function () {
           $("#username").blur(function () {
               //注册onblur事件,取出uName的值
              var uName = this.value;
              //不为空时调用
                  $.get("UserServlet?reqType=checkName&username="+uName,function (data,status) {
                      //data是后台响应回来的字符串
                      //表明是true
                      if(data == 'true'){//说明有重复的
                          document.getElementById("username_notice").innerHTML = "昵称已存在";
                          $("#username").focus();
                          $("#username").select();
                          return null;
                          //然后还要选中
                      } else{
                          document.getElementById("username_notice").innerHTML = "<img src='images/icon_gou.gif'/>";
                      }
                  });
             });

           });
    </script>
    <script>
       function checkQQ(val) {
           //当输入英文字母时
           if (val == '') {
               document.getElementById("qq_notice").innerHTML = "不能为空";
               document.getElementById("qq").focus();
           } else if (isNaN(Math.round(Number(val)))) {
               document.getElementById("qq_notice").innerHTML = "不是数字";
               $("#qq").focus().select();
           } else {
               document.getElementById("qq_notice").innerHTML = "<img src='images/icon_gou.gif'/>";
           }
       }

           function checkEmail(val){
               if (val == ''){
                   document.getElementById("email_notice").innerHTML = "邮箱不能为空";
                   document.getElementById("email").focus();
               } else if ( val.length <= 10) {
                   document.getElementById("email_notice").innerHTML = "邮箱格式不合法!";
                   document.getElementById("email").focus();
               } else {
                   document.getElementById("email_notice").innerHTML = "<img src='images/icon_gou.gif'/>";
               }
           }

           function checkPwd(val){
               if (val==''){
                   document.getElementById("password_notice").innerHTML = "密码不能为空";
                   document.getElementById("password").focus();
               }else{
                   document.getElementById("password_notice").innerHTML =  "<img src='images/icon_gou.gif'/>";
               }


           }


           function checkPwd1(val){
               if (val==''){
                   document.getElementById("confirm_password_notice").innerHTML = "密码不能为空";
                   document.getElementById("confirm_password").focus();
               }else{
                   document.getElementById("confirm_password_notice").innerHTML =  "<img src='images/icon_gou.gif'/>";
               }
           }

        function checkForm(form) {
            //判断是否被选中
            // alert(document.getElementById("agreement").checked)
            // alert(form.action)
            //在这个方法中统一控制
            //验证用户名,等一下还要验证用户名是否重复,
            var username = form.username.value;
            var pwd = form.pwd.value;
            var pwd1 = form.confirm_password.value;
            var checkedBox = document.getElementById("agreement").checked;
            if (form.username.value == ''){
                document.getElementById("username_notice").innerHTML = "用户名不能为空";
                form.username.focus();
                return false;
            }
            
            //emai
            
            //passward,要验证密码是否为空，还有两次输入的密码是否一致
            if (form.pwd.value == ''){
                document.getElementById("password_notice").innerHTML = "密码不能为空!";
                form.pwd.focus();
                return false;
            }

            if (form.confirm_password.value !== form.pwd.value){
                document.getElementById("confirm_password_notice").innerHTML ="两次输入的密码不一致!";
                form.confirm_password.focus();
                return false;
            }



            if (checkedBox == false) {
                return false;
                window.confirm("您没有勾选协议!")
            }else{
                if (username ==''||pwd1==''||qq ==''||pwd==''){
                    return false;
                } else{
                    return true;
                }
            }
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
<div class="blank"></div>

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

<div class="usBox">
    <div class="usBox_2 clearfix">
        <div class="regtitle"></div>                                                    <!--返回true可以提交，返回false不能提交-->
        <form action="UserServlet?reqType=register" method="post" name="formUser" onsubmit="return checkForm(this)">
            <table width="100%" border="0" align="left" cellpadding="5" cellspacing="3">
                <tr>
                    <td width="11%" align="right">用户名</td>
                    <td width="89%">
                        <input name="username" type="text" size="25" id="username"
                                class="inputBg"/>
                        <span id="username_notice" style="color:#FF0000"> *</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">Email</td>
                    <td>
                        <input name="email" type="text" size="25" id="email" onblur="checkEmail(this.value)"
                               class="inputBg"/>
                        <span id="email_notice" style="color:#FF0000"> *</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">密码</td>
                    <td>
                        <input name="pwd" type="password" id="password"  class="inputBg" onblur="checkPwd(this.value)" />
                        <span style="color:#FF0000" id="password_notice"> *</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">确认密码</td>
                    <td>
                        <input name="confirm_password" type="password" id="confirm_password" class="inputBg" onblur="checkPwd1(this.value)" />
                        <span style="color:#FF0000" id="confirm_password_notice"> *</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">QQ</td>
                    <td>
                        <input name="qq" id="qq" type="text" size="25" class="inputBg" onblur="checkQQ(this.value)" />
                        <span style="color:#FF0000" id="qq_notice"> *</span>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><label><!--此处需要控制若没有勾选,则不能登录-->
                        <input id="agreement" name="agreement" type="checkbox" value="1"  />
                        我已看过并接受《<a href="##" style="color:blue" target="_blank">用户协议</a>》</label>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="left">
                        ${requestScope.registInfo}
                        <input name="Submit" type="submit" value="" class="us_Submit_reg"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="actionSub">
                        <a href="login.jsp">我已有账号，我要登录</a><br/>
                        <a href="###">您忘记密码了吗？</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div class="blank5"></div>

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
                <a href="#top"><img src="images/bnt_top.gif"/></a>
                <a href="###"><img src="images/bnt_home.gif"/></a>
            </div>
        </div>
    </div>
</div>
<div class="blank"></div>

<!--版权信息栏-->
<div class="text" align="center">
    &copy; 2010-2015 网上商城 版权所有，并保留所有权利。<br/>
    E-mail: 123456@qq.com<br/>
    ICP备案证书号:<a href="###" target="_blank">粤ICP备1234568</a><br/>
    <div align="center" id="rss"><a href="###"><img src="images/xml_rss2.gif" alt="rss"/></a></div>
</div>
</body>
</html>
