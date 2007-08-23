

function onValueClicked(id, field_name)
{
	var nm=document.getElementById(id);
	var el=createElement(id,field_name,nm.firstChild.nodeValue);
	
	nm.parentNode.replaceChild(el,nm);

	el.focus();

}

function onValueMouseOver(id)
{
	var nm=document.getElementById(id);
	nm.style.border="1px solid #445";
}

function onValueMouseOut(id)
{
	var nm=document.getElementById(id);
	nm.style.border="0px solid #445";
}

function createElement(id,field_name,value)
{
	var values=terms.get(field_name);
	var el=null;
	if(values==null)
	{
		el=document.createElement("input");
		el.setAttribute("name",id);
		el.setAttribute("id",id);
		el.setAttribute("type","text");
		el.setAttribute("value",value);
		
	}
	else
	{
		el=document.createElement("select");
		el.setAttribute("name",id);
		el.setAttribute("id",id);
		for(var i=0;i<values.length;i++)
		{
			var defaultSelected=false;
			var selected=false;
			if(i==0)
				defaultSelected=true;
			if(value==values[i])
				selected=true;
				
			el.options[i]=new Option(values[i],values[i],defaultSelected, selected);
			if(selected)el.options[i].setAttribute("selected","yes");
		}
	}
	
	return el;
}