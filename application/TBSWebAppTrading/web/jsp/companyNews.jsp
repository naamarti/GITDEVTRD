<%@ include file="template/pageDocTypeHTML.jspf" %>
<%@ include file="template/pageInitializeSettings.jspf" %>

<%-- ********** LAYOUT STANDARD ********** --%>
<c:set var="javaScriptFile" value="companyNews_v0003.js" />
<c:set var="javaScriptOnReady" value="jScript.companyNews.init();" />
<c:set var="layoutShowIconBar" value="1" />
<%@ include file="template/layoutStandardHeaderNew.jspf" %>

	<div style="margin-left:18px; margin-top:3px; width:${layoutMenuTopWidth -5}px; background-color:#FFFFFF;" >
		<span class="tbs-ui-breadcrumb-filter" >${model.user.userName}</span>
		<span class="tbs-ui-breadcrumb-link" onClick="jScript.menu.navigateToURL('accountHome.htm')">Account Home</span>
		<span class="tbs-ui-breadcrumb" >&nbsp;>&nbsp;</span>
		<span class="tbs-ui-breadcrumb" >Company News</span>
	</div>

	<%-- ********** PAGE FILTER ********** --%>
	<div id="divPageFilter" name="divPageFilter" class="tbsStylePageFilter tbsStyleRounded" style="margin-left:9px; margin-top:10px; width:945px; height:45px;" >
		<div class="tbsStylePageFilterImage" >
			<img src="images/news1_48.png" height="36px;" width="36px;" style="border-style:none;" alt=""/>
		</div>
		<div style="margin-top:9px;" >
			<span class="tbs-ui-pagefilter-screentitle">Company News</span>
		</div>
	</div>

	<div style="margin-left:9px; margin-top:15px; width:945px; height:25px;" >
		<div class="tbs-ui-page-section-bar" >
			Documents Available For Download
		</div>
	</div>
	
	<div style="margin-left:9px; margin-top:15px; width:150px; height:70px;" >
		<td valign="top">
          <a href="http://www.adobe.com/products/acrobat/readstep2.html" target="new">Download Adobe Acrobat</href>
          <br>
          <a href="http://www.adobe.com/products/acrobat/readstep2.html" target="new">
            <img src="images/acrobat_reader_download.gif" width="88" height="31" border="0" alt="Download Adobe Acrobat" />
          </a>
        </td>
    </div>
    
	<div style="margin-left:100px; margin-top:5px; width:400px;" >
		

	    <table border="0" cellpadding="0" cellspacing="0">
	
	          <tr style="height:30px;">
	            <td>
	              <a onclick="jScript.companyNews.popUpPDF('UnitHolder_Letter_021513');" href="javascript:void(0);"><img src="images/pdf2_32.png" border="0" alt="PDF"></a>
	              <a onclick="jScript.companyNews.popUpPDF('UnitHolder_Letter_021513');" href="javascript:void(0);">Current Unitholder Letter</a>
	            </td>
	          </tr>       
	          
	          <tr style="height:30px;">
	            <td>
	              <a onclick="jScript.companyNews.popUpPDF('UnitHolder_Letter_072412');" href="javascript:void(0);"><img src="images/pdf2_32.png" border="0" alt="PDF"></a>
	              <a onclick="jScript.companyNews.popUpPDF('UnitHolder_Letter_072412');" href="javascript:void(0);">July 2012 Unitholder Letter</a>
	            </td>
	          </tr>       
	
	    </table>

	</div>
	
			
<%@ include file="template/layoutStandardFooterNew.jspf" %>
