function toggleLayer( whichLayer ){
  var elem, vis;
  if( document.getElementById ) // this is the way the standards work
    elem = document.getElementById( whichLayer );
  else if( document.all ) // this is the way old msie versions work
      elem = document.all[whichLayer];
  else if( document.layers ) // this is the way nn4 works
    elem = document.layers[whichLayer];
  vis = elem.style;
  
  if(vis.display==''&& elem.offsetWidth!=undefined && elem.offsetHeight!=undefined)
    vis.display = (elem.offsetWidth!=0 && elem.offsetHeight!=0)?'block':'none';
  vis.display = (vis.display=='' || vis.display=='block')?'none':'block';
}

function trim(stringToTrim) {
    if(stringToTrim==null) return "";
    if(stringToTrim=="") return "";
    return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}


function changeType(obj,type){
    if(!dojo.isIE){
	obj.setAttribute("type",type);
    }else{
	var newO=document.createElement('input');
	newO.setAttribute('type',type);
	newO.setAttribute('name',obj.getAttribute('name'));
	newO.setAttribute('id',obj.getAttribute('id'));
	newO.value=obj.value;
	obj.parentNode.replaceChild(newO,obj);
    }
}

function addEventHandler(obj,ev,handler){
    if(dojo.isIE){
	obj.attachEvent("on"+ev,handler);
    }else{
	obj.addEventListener(ev,handler,false);
    }
}

function getTarget(e){
    var trigger='';
    if(dojo.isIE){
	if(window.event.srcElement){
	    trigger= window.event.srcElement;
	}
    }else{	
    
	if(e.currentTarget){
	    trigger= e.currentTarget;
	}else if(e.target){
	    trigger= e.target;
	}
    }
    
    if (trigger.nodeType && trigger.nodeType== 3){
	alert(trigger.nodeType);
	trigger= trigger.parentNode;
    }
    return trigger;
}

