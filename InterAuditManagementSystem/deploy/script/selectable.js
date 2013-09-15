function initSelectable()
{
  var elem = "TR";
  if(document.getElementsByTagName)
  {
    var el = document.getElementsByTagName(elem);
      
    for(var i=0; i<el.length; i++) 
    {
      try
      {
	      if(el[i].childNodes[0].tagName != "TH" && el[i].parentNode.parentNode.className.indexOf("selectable") != -1)
	      {
	        if(i%2 == 1)
	        {  
	      	  el[i].className = "on";
	      	  el[i].oldClassName = "on";
	      	  el[i].onmouseout = function()
	    	    {
	            this.className = "on";
	          }
		      } 
		      else 
		      {
	    	    el[i].className = "off";
	          el[i].oldClassName = "off";
	          el[i].onmouseout = function()
	          {
	            this.className = "off";
	          }
	        }
	    	  el[i].onmouseover = function()
	    	  {
	          if(this.className == this.oldClassName)
	          {
	            this.className = "hover";
	          }
	          if(this.onmouseout == null && this.className != "click")
	          {
	            this.onmouseout = function()
	            {
	              this.className = this.oldClassName;
	      	    }
	          }
	        }
				  el[i].onclick = function()
				  {
				    document.body.style.cursor = 'wait';
	     	    if(this.className != "click")
	     	 	  {
	            this.className = "click";
		        } 
		       
	     	    for(var i=0; i<el.length; i++)
	     	    {
	     	  	  if (el[i] != this)
	     	  	  {
	     	  	    if(i%2 == 1)
	              {
	                el[i].className = "on";
	                  el[i].oldClassName = "on";
	              }
	              else
	              {
	                el[i].className = "off";
	                  el[i].oldClassName = "off";
	              }  
		      	  }
		      	} 
		      	this.onmouseout = null; 
		      	var ie = (navigator.appName.indexOf("Internet Explorer") !=-1) ? true: false;
				    if (ie)
				    {
				      this.fireEvent("ondblclick");
				    }
				    else
				    {  
				      var evt = this.ownerDocument.createEvent('MouseEvents');
						  evt.initMouseEvent('dblclick', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
						  this.dispatchEvent(evt);
						}
	 				}
	 			}
 			} catch(error) {}
    }
  }
}