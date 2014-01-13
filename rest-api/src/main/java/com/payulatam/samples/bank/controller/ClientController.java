package com.payulatam.samples.bank.controller;

/*import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
*/
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/hola")
public class ClientController {

	/*@Autowired
	private GigaSpace gigaSpace;
*/
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Dog sayHello() {
		//return new Dog("simon"+gigaSpace.getName());
		return new Dog("Simon");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Dog changeGroup(@PathVariable String id) {
		return new Dog("" + id);
	}

}