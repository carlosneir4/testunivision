package co.com.univision.widgets.controller;


import co.com.univision.widgets.domain.WidgetDTO;
import co.com.univision.widgets.service.UnivisionWidgetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;


@Slf4j
@RestController
@RequestMapping("/univision")
public class UnivisionWidgetsController {

    @Autowired
    private UnivisionWidgetsService univisionWidgetsService;


    @RequestMapping("/widgets")
    public Observable<WidgetDTO> getWidgets(@RequestParam(value = "path", defaultValue = "") String path) {
        log.info("Retrieving widgets info");
        return univisionWidgetsService.getWidgets(path);
    }
}
