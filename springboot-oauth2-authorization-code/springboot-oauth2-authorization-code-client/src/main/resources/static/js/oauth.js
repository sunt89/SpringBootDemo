$(document).ready(function(){

var oauthParams = {},
	queryString = location.hash.substring(1),
	regex = /([^&=]+)=([^&]*)/g,
	m,
	html='';

while (m = regex.exec(queryString))
{
	oauthParams[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
}

console.log(oauthParams);

for (pos in oauthParams)
	html += pos + '=' + oauthParams[pos] + '<br>';

$('#oauthParams').html(html);

});