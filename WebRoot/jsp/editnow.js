

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
		if(value.length<30)
		{
			el=document.createElement("input");
			el.setAttribute("name",id);
			el.setAttribute("id",id);
			el.setAttribute("type","text");
			el.setAttribute("value",value);
		}
		else
		{
			el=document.createElement("textarea");
			el.setAttribute("name",id);
			el.setAttribute("id",id);
			el.setAttribute("cols",45);
			el.setAttribute("rows",5);
			
			var txt=document.createTextNode(value);
			el.appendChild(txt);
		}
		
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
		el.options[i]=new Option("","",false,false);
	}
	
	return el;
}

function onFieldChange(e)
{
	var id;
	if(e.srcElement)
		id=e.srcElement.id;
	else
		id=e.target.id;
	var vid=id-1;	
	var oField=document.getElementById(id);
	var nm=document.getElementById(vid);
	var field_name=oField.value;
	var el=createElement(vid,field_name,"");
	nm.parentNode.replaceChild(el,nm);
	el.focus();
	
}
function onFieldClicked(id, namespace)
{
	var nm=document.getElementById(id);
	var el;
	if(namespace!="NewEnvironmentStudyAttribute")
	{
		el=document.createElement("select");
		el.setAttribute("name",id);
		el.setAttribute("id",id);
	
		if (el.addEventListener) {
			el.addEventListener ("change",onFieldChange,false);
		} else if (el.attachEvent) {
			el.attachEvent ("onchange",onFieldChange);
		} else {
			el.onchange = onFieldChange;
		}
		
		if(namespace=="NewDescriptorAttribute")
		{
			for(var i=0;i<attrs.length;i++)
			{
				el.options[i]=new Option(attrs[i],attrs[i],false, false);
			}
		}
		else if(namespace=="NewPopulationSampleAttribute")
		{
			
			for(var i=0;i<pop_sample_attributes.length;i++)
			{
				el.options[i]=new Option(pop_sample_attributes[i],pop_sample_attributes[i],false, false);
			}
		}
	}
	else
	{
		el=document.createElement("input");
		el.setAttribute("name",id);
		el.setAttribute("id",id);
		el.setAttribute("type","text");
		var vid=id-1;
		var vnm=document.getElementById(vid);
		var field_name=namespace;
		var vel=createElement(vid,field_name,"");
		vnm.parentNode.replaceChild(vel,vnm);
	
	}
	
	
	
	nm.parentNode.replaceChild(el,nm);

	

	
	el.focus();

}

function onFieldMouseOver(id)
{
	var nm=document.getElementById(id);
	nm.style.border="1px solid #445";
	
	
}

function onFieldMouseOut(id)
{
	var nm=document.getElementById(id);
	nm.style.border="0px solid #445";
}