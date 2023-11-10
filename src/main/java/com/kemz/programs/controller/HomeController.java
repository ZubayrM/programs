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
import java.util.Map;
import java.util.Optional;
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

    private static final int DETAIL_SIZE = 15;

    private static Integer totalPages;
    private static Integer numberPage;

    private String search = null;

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
    public HomeDto homeDto() {
        Page<Detail> details = homeService.getDetails(PageRequest.of(0, DETAIL_SIZE));
        totalPages = details.getTotalPages();
        numberPage = details.getNumber();



        return HomeDto.builder()
                .details(details.getContent())
                .programs(new ArrayList<Program>())
                .tools(new ArrayList<Tool>())
                .urlImg(null)
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

        if (search != null) return search(search, homeDto, value);


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
        homeDto.setUrlImg(null);
        homeDto.setDetailActive(detailId);
        homeDto.setPrograms(homeService.getPrograms(detailId));
        homeDto.setTools(new ArrayList<>());
        homeDto.setProgramActive(null);
        homeDto.setUrlImg(null);

        return "redirect:/home";
    }

    @GetMapping("tools/{program_id}")
    public String getTools(@PathVariable(name = "program_id") Long programId, @ModelAttribute(name = "home_dto")  HomeDto homeDto){
        homeDto.setProgramActive(programId);
        homeDto.setTools(homeService.getTools(programId));
        try {
            homeDto.setImgByte(imageRepo.findByProgramId(programId).orElseThrow().getImg());
        } catch (Exception e){
            log.info(e.getMessage());
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
    public String downloadProgram(@PathVariable(name = "program_id") String programId, @ModelAttribute("home_dto") HomeDto homeDto) throws IOException {
        String[] strings = programId.split("&");
        Program program = homeService.getProgram(Long.valueOf(strings[0]));

        switch (strings[1]) {
            case "fanuc": return program.getCodeFanuc();
            case "nc": return program.getCodeHaas();
            case "h": return program.getCodeH();
            default: return "";
        }
    }

    @PostMapping("/search")
    public String search(@RequestParam("text") String text, @ModelAttribute("home_dto") HomeDto homeDto, Integer value ){
        search = text;
        if (value==null || value < 0) value= 0;

        Page<Detail> detailBySearch = homeService.getDetailBySearch(text, PageRequest.of(value, DETAIL_SIZE));
        if (value >= detailBySearch.getTotalPages()) return "redirect:/home";
        homeDto.setDetails(detailBySearch.getContent());
        homeDto.setTotalPages(detailBySearch.getTotalPages());
        homeDto.setNumberPage(detailBySearch.getNumber());
        return "redirect:/home";
    }


    @PostMapping("addDetail")
    public String addDetail(String cipher, String name, @ModelAttribute("home_dto") HomeDto homeDto){
        Long detailId = detailService.add(cipher, name);
        homeDto.setDetails(homeService.getAllDetail());
        return "redirect:/home/programs/" + (detailId != 0L ? detailId : "");
    }

    @PostMapping("/addImg")
    public String addImg(@RequestParam("img") MultipartFile file, @ModelAttribute("home_dto") HomeDto homeDto) throws IOException {
        Optional<Image> programId = imageRepo.findByProgramId(homeDto.getProgramActive());
        programId.ifPresent(imageRepo::delete);
        Image image = imageRepo.save(Image.builder()
                .img(file.getBytes())
                .type(file.getContentType())
                .programId(homeDto.getProgramActive())
                .build());
        homeDto.setImgByte(image.getImg());
        homeDto.setUrlImg(URL_IMG);
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
