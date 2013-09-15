function initToolGroup()
{
  var el0em = "DIV";
  if(document.getElementsByTagName)
  {
    var el0 = document.getElementsByTagName(el0em);
    for(var i=0; i<el0.length; i++) 
    {
      try
      {
	      if(el0[i].className.indexOf("menuitem") != -1)
	      {
	        el0[i].oldClassName = el0[i].className;
	        el0[i].onmouseover = function()
	    	  {
	          if(this.className == this.oldClassName)
	          {
	            this.className = "thover";
	          }
	          if(this.onmouseout == null && this.className != "tclick")
	          {
	            this.onmouseout = function()
	            {
	              this.className = this.oldClassName;
	      	    }
	          }
	        }
	        
	        el0[i].onmousedown = function()
		      {
		        if(this.className != "tclick")
	     	 	  {
	            this.className = "tclick";
		        }
		      }  
	        
				  el0[i].onclick = function(e)
				  {
				    document.body.style.cursor = 'wait';
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
		      
		      el0[i].onmouseup = function()
		      {
		        if(this.className != "thover")
	     	 	  {
	            this.className = "thover";
   		      }
		      }  
	 			}
 			} catch(error) {}
    }
  }
}