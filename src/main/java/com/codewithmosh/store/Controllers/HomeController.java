package com.codewithmosh.store.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model)    //Model is basically a container for data. we can use it to pass the data from controller to view
    {
        model.addAttribute("name","rahul");  //this is how we use the template engine to render data dynamically.
        return "index";
    }

//    @RequestMapping("/hello")
//    public String sayHello()
//    {
//        return "index.html";
//    }

}
