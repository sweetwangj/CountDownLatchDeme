package com.boot.swaggercountdownlatch.controller.other;


import com.boot.swaggercountdownlatch.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@Api(tags = "2.19", description = "处理定时任务", value = "处理定时任务")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/{id}")
    @ApiOperation(value = "定时任务处理（DONE）")
    public String file(@PathVariable Long id) {
        if(id==1l)
        {
            taskService.doTaskV1();
        }else
        {
            taskService.doTaskV2();
        }

        return "任务执行成功";
    }


}
