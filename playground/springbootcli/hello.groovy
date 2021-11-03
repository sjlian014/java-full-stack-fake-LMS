@Controller
class Example {
   @RequestMapping("/custom_path")
   @ResponseBody
   public String hello() {
       "hello"
   }

   @RequestMapping("/another_custom_path")
   @ResponseBody
   public String goodbye() {
       "goodbye"
   }
}