<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,com.rjxy.lh.entity.*"
    pageEncoding="UTF-8"%>
   <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>WebChat | 聊天</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="static/plugins/amaze/css/amazeui.min.css">
    <link rel="stylesheet" href="static/plugins/amaze/css/admin.css">
    <link rel="stylesheet" href="static/plugins/contextjs/css/context.standalone.css">
    <script src="static/plugins/jquery/jquery-2.1.4.min.js"></script>  
    <script src="static/plugins/amaze/js/amazeui.min.js"></script>
    <script src="static/plugins/amaze/js/app.js"></script>
    <script src="static/plugins/layer/layer.js"></script>
    <script src="static/plugins/laypage/laypage.js"></script>
    <script src="static/plugins/contextjs/js/context.js"></script>    
</head>
<body>
<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <i class="am-icon-weixin"></i> <strong>WebChat</strong> <small>网页聊天室</small>
    </div>
    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li class="am-dropdown" data-am-dropdown>
                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    ${loginUser.nickname }
                    <%
                    List<Message>checkList = (List<Message>)request.getAttribute("checkList");
                    if (checkList.size()>0) {
                    		/* out.print("[<span href='javascript:showCheckDiv()'>"+checkList.size()+"]"); */
                    		out.print("[<span onclick='showCheckDiv()'>"+checkList.size()+"]");
                    }
                    %>
                    <span class="am-icon-caret-down"></span>
                </a>
            </li>
        </ul>
    </div>
    <div id="checkDiv" style="width: 280px; height: 300px; 
    top: 60px; left: 750px; border: solid 1px #abc; position: absolute; display: none;">
   <%
	    for(int i=0;i<checkList.size();i++){
	    Message message = checkList.get(i);
	%>
	  <%=message.getContent() %> <a href="javascript:hideCheckDiv('<%=message.getId() %>',1)">同意</a>|<a href="javascript:hideCheckDiv('<%=message.getId() %>',0)">拒绝</a><br/>;
	    <%
	    }
     %>
    </div>
    <script type="text/javascript">
    // 显示请求消息
    function showCheckDiv() {
    		document.getElementById("checkDiv").style.display="block"; 
	}
    // 处理请求
    function hideCheckDiv(mid, op ){
    		document.getElementById("checkDiv").style.display="none";
    		location.href="chatUser/checkmessage.do?mid="+mid+"&resu="+op;	
    }
    </script>
    
</header>
<div class="am-cf admin-main">
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul class="am-list admin-sidebar-list">
            <li><a href="index.html"><span class="am-icon-commenting"></span> 聊天</a></li>
            <li><a href="information.html" class="am-cf"><span class="am-icon-book"></span> 个人信息<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
            <li class="admin-parent">
                <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="am-icon-cogs"></span> 设置 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
                    <li><a href="info-setting.html"><span class="am-icon-group"></span> 个人设置</a></li>
                    <li><a href="user_space.html"><span class="am-icon-cog"></span>心情动态</a></li>                 
                </ul>
            </li>
           <li><a href="chatUser/notfriends.do"><span class="am-icon-globe"></span>好友动态</a></li>        
            <li><a href="log.html"><span class="am-icon-inbox"></span> 系统日志<span class="am-badge am-badge-secondary am-margin-right am-fr">24</span></a></li>
            <li><a href="login.html"><span class="am-icon-sign-out"></span> 注销</a></li>
        </ul>
        <div class="am-panel am-panel-default admin-sidebar-panel">
            <div class="am-panel-bd">
                <p><span class="am-icon-tag"></span> Welcome</p>
                <p>欢迎使用WebChat聊天室~</p>
            </div>
        </div>
    </div>
</div>
<!-- sidebar end -->
    <!-- content start -->
    <div class="admin-content"  >
        <div class="" style="width: 80%;float:left;">
            <!-- 聊天区 -->
            <div class="am-scrollable-vertical" id="chat-view" style="height: 310px;">
                <ul class="am-comments-list am-comments-list-flip" id="chat">
                <%
                	if(request.getAttribute("messageList")!=null) {
                		// 获取登录者
                		ChatUser loginuser = (ChatUser)session.getAttribute("loginUser");
                		List<Message> messageList = (List<Message>)request.getAttribute("messageList");
                		for (int i = 0; i < messageList.size(); i++) {
                			Message message = messageList.get(i);
                			// 判断是否是登录用户发送出去的消息
                			if (message.getFromid().equals(loginuser.getId())){
                %>
                <li class="am-comment am-comment-flip am-comment-primary"><a href="#link-to-user-home"><img width="48" height="48" class="am-comment-avatar" alt="" src="static/source/img/default_head.jpg"></a><div class="am-comment-main">
                <header class="am-comment-hd"><div class="am-comment-meta">   
                <a class="am-comment-author" href="#link-to-user"><%=loginuser.getNickname() %></a> 发表于<time><%=message.getTime() %></time> 发送给:<%=message.getToid() %> </div></header><div class="am-comment-bd"> <p><%=message.getContent() %></p></div></div></li>
                <%}else{ %>
                <li class="am-comment am-comment-primary">
                <a href="#link-to-user-home">
                	<img width="48" height="48" class="am-comment-avatar" alt="" src="static/source/img/default_head.jpg"></a>
                <div class="am-comment-main">
                <header class="am-comment-hd">
                <div class="am-comment-meta">   
                <a class="am-comment-author" href="#link-to-user"><%=message.getFromid() %></a> 发表于
                <time><%=message.getTime() %></time> 发送给:<%=loginuser.getNickname() %> </div>
                </header>
                <div class="am-comment-bd"> <p><%=message.getContent() %></p></div>
                </div></li> 
                <%}}} %>                             
                </ul>
            </div>
            <!-- 输入区 -->
			<form action="chatUser/addmessage.do" method="post">
	            <div class="am-form-group am-form">
	            	<input type="hidden" name="toid" id="toid" />
	                <textarea class="" id="message" name="content" rows="5"  placeholder="这里输入你想发送的信息..."></textarea> 
	            </div>
	            <!-- 接收者 -->
	            <div class="" style="float: left">
	                <p class="am-kai">发送给 : <span id="sendto">赵丽丽</span></p>
	            </div>
	            <!-- 按钮区 -->
	            <div class="am-btn-group am-btn-group-xs" style="float:right;"> 
	                <button class="am-btn am-btn-default" type="submit"><span class="am-icon-commenting"></span> 发送</button>
	            </div>
            </form>
        </div>
        <!-- 列表区 -->
        <div class="am-panel am-panel-default" style="float:right;width: 20%;">
	        	<%
	        		List<ChatUser> friendList = (List<ChatUser>)request.getAttribute("friendList");
	        %>
            <div class="am-panel-hd">
                <h3 class="am-panel-title">好友列表 [<%=friendList.size() %>]</h3>
            </div>
            <%
            		for (int i = 0; i < friendList.size(); i++) {
            			ChatUser friend = friendList.get(i);
			%>
            <ul class="am-list am-list-static am-list-striped" >
                <li><%=friend.getNickname() %>
                <%if(friend.getChecktype()>0){ %>
                <button class="am-btn am-btn-xs am-btn-danger" id="tuling" 
                onclick="readmessage('<%=friend.getId() %>')" data-am-button><%=friend.getChecktype() %></button></li>
                <%}else{ %>
                		<button type="button" class="am-btn am-btn-xs am-btn-primary am-round" 
                		onclick="addChat('<%=friend.getNickname() %>', '<%=friend.getId() %>');"><span class="am-icon-phone"><span> 私聊</button></li>
                <%} %>
            </ul>
            <%}%>
            <script type="text/javascript">
            		function readmessage(id) {
					location.href="<%=basePath%>chatUser/main.do?friendid="+id;
					alert(id);
				}
				function addChat(nickname, id) {
					alert("nickname:"+nickname+",id:"+id);
					document.getElementById("sendto").innerHTML=nickname;
					document.getElementById("toid").value=id;
				}
            </script>
            
            <!-- <ul class="am-list am-list-static am-list-striped" id="list">
            <li>赵丽丽
            <button type="button" class="am-btn am-btn-xs am-btn-primary am-round" onclick="addChat('赵丽丽');"><span class="am-icon-phone"><span> 私聊</button></li>
            </ul> -->
        </div>
    </div>
    <!-- content end -->
</div>
<a href="#" class="am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}">
    <span class="am-icon-btn am-icon-th-list"></span>
</a>
<footer style="text-align: center">
    <hr>
<p class="am-padding-left">© 2016 <a href="http://www.qianchengzhidao.com">oracle北京实训基地</a>. </p>
</footer>


</body>
</html>