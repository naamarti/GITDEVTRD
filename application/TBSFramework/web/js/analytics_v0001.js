var analyticsScript         = new Object();

var _gaq 					= _gaq || [];
_gaq.push(['_setAccount', 'UA-29628232-1']);
_gaq.push(['_trackPageview']);
 

analyticsScript.doAnalytics = function()
{
	var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
}