package com.kemz.programs.controller;

import com.kemz.programs.Repo.ImageRepo;
import com.kemz.programs.dto.HomeDto;
import com.kemz.programs.model.*;
import com.kemz.programs.service.DetailService;
import com.kemz.programs.service.HomeService;
import com.kemz.programs.service.Printer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Log4j2
@Controller
@RequestMapping("home")
@SessionAttributes("home_dto")
@ConfigurationPropertiesScan
public class HomeController {

    @Value("${url-img}")
    private String URL_IMG;

    private final HomeService homeService;
    private final DetailService detailService;
    private final ImageRepo imageRepo;

    private static final int DETAIL_SIZE = 14;

    private static Integer totalPages;
    private static Integer numberPage;

    @Autowired
    public HomeController(HomeService homeService, DetailService detailService, ImageRepo imageRepo) {
        this.detailService = detailService;
        this.imageRepo = imageRepo;
        this.homeService = homeService;
    }

    @ModelAttribute("msg")
    public Message getText(){
        return new Message();
    }

    @ModelAttribute("home_dto")
    public HomeDto homeDto(){
        Page<Detail> details = homeService.getDetails(PageRequest.of(0, DETAIL_SIZE));
        totalPages = details.getTotalPages();
        numberPage = details.getNumber();



        return HomeDto.builder()
                .details(details.getContent())
                .programs(new ArrayList<Program>())
                .tools(new ArrayList<Tool>())
                .urlImg(URL_IMG)
                .numberPage(numberPage)
                .totalPages(totalPages)
                .build();
    }

    @GetMapping
    public String home(Principal principal, @ModelAttribute("home_dto") HomeDto homeDto){
        homeDto.setAuth(principal != null);
        return "home";
    }


    @GetMapping("/{value}")
    public String home(@ModelAttribute("home_dto") HomeDto homeDto, @PathVariable Integer value){


        Page<Detail> detailPage = homeService.getDetails(PageRequest.of(value < 0 ? 0 : value , DETAIL_SIZE));

        if (value != detailPage.getTotalPages()) {
            homeDto.setNumberPage(detailPage.getNumber());
            homeDto.setTotalPages(detailPage.getTotalPages());
            homeDto.setDetails(detailPage.getContent());
        }
        return "redirect:/home";
    }



    @GetMapping("programs/{detail_id}")
    public String getPrograms(@PathVariable(name = "detail_id") Long detailId, @ModelAttribute(name = "home_dto") HomeDto homeDto){
        homeDto.setDetailActive(detailId);
        homeDto.setPrograms(homeService.getPrograms(detailId));
        homeDto.setTools(new ArrayList<>());

        return "redirect:/home";
    }

    @GetMapping("tools/{program_id}")
    public String getTools(@PathVariable(name = "program_id") Long programId, @ModelAttribute(name = "home_dto")  HomeDto homeDto){
        homeDto.setProgramActive(programId);
        homeDto.setTools(homeService.getTools(programId));
        try {
            homeDto.setImgByte(imageRepo.findByProgramId(programId).orElseThrow().getImg());
        } catch (Exception e){

        }

        return "redirect:/home";
    }

    @GetMapping("addProgramPage")
    public String addPage(@ModelAttribute("home_dto") HomeDto homeDto, @ModelAttribute("program") Program program){
        program.setDetailId(homeDto.getDetailActive());
        program.setUserId(1L);
        return "addProgramPage";
    }

    @GetMapping(value = "down/program/{program_id}")
    @ResponseBody
    public String downloadProgram(@PathVariable(name = "program_id") Long programId) throws IOException {
        return homeService.getProgram(programId);
    }

    @PostMapping("/search")
    public String search(@RequestParam("text") String text, @ModelAttribute("home_dto") HomeDto homeDto ){
        homeDto.setDetails(homeService.getDetailBySearch(text));
        return "redirect:/home";
    }


    @PostMapping("addDetail")
    public String addDetail(String cipher, String name, @ModelAttribute("home_dto") HomeDto homeDto){
        Long detailId = detailService.add(cipher, name);
        log.info(String.format("add detail: id- %d name- %s cipher- %s", detailId, name, cipher));
        homeDto.setDetails(homeService.getAllDetail());
        return "redirect:/home/programs/" + detailId;
    }

    @PostMapping("/addImg")
    public String addImg(@RequestParam("img") MultipartFile file, @ModelAttribute("home_dto") HomeDto homeDto) throws IOException {
        Image image = imageRepo.save(Image.builder()
                .img(file.getBytes())
                .type(file.getContentType())
                .programId(homeDto.getProgramActive())
                .build());
        homeDto.setImgByte(image.getImg());
        return "redirect:/home";
    }

    @GetMapping("/addImagePage")
    public String addImgPage(){
        return "addImagePage";
    }

    @GetMapping(value = "img/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] openImg(@PathVariable Long id) {
        byte[] img;
        try {
            img = imageRepo.findByProgramId(id).orElseThrow().getImg();
        } catch (Exception e){
            img = new byte[0];
        }
        return img;
    }

    @GetMapping("/print")
    public String printImg(@ModelAttribute(name = "home_dto") HomeDto dto){
        log.info(String.format("Размер фото: %s",dto.getImgByte().length));
        try {
            Printer.printImg(new ByteArrayInputStream(dto.getImgByte()));
        } catch (Exception e){
            log.info(String.format("Печать не пошла потому что %S", e.getMessage()));
        }

        return "redirect:/home";
    }

    @GetMapping("/printTools")
    public String printHtml( @ModelAttribute("home_dto") HomeDto homeDto){
        Map<String,Object> map = new TreeMap<>();
        homeDto.getTools().forEach(tool ->
            map.put(tool.getCipher(), tool.getName()));
        Printer.printTools("toolList.html",map);
        return "redirect:/home";
    }

}
