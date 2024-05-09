package com.example.taskservice.Service;

import com.example.taskservice.Dto.ScheduleClassDto;
import com.example.taskservice.Dto.SelectedClassDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TASKSERVICE {
    @Autowired
    private WebClient webClient;
    @Value("${apigateway.url}")
    private String ApiGateway;

    public Mono<List<ScheduleClassDto>> getAllschedule() {

        return webClient.get()
                .uri(ApiGateway+"/scheduleclass")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ScheduleClassDto>>() {
                });
    }

    public Mono<List<SelectedClassDto>> getAllSelectedClass() {
        return webClient.get()
                .uri(ApiGateway+"/selectedclass")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SelectedClassDto>>() {
                });
    }

    public Mono<Void> RegisterSubject(int id) {
        //gọi chung đến apigateway, tương tác với service nào thì /service ấy
        //gọi đến ScheduleClassservice để check còn slot hay không và trả về 1 đối tượng lịch học (scheduleclass)
        Mono<ScheduleClassDto> checkCapacity = webClient
                .post()
                .uri(ApiGateway+"/scheduleclass/checkcapacity/" + id)
                .retrieve()
                .bodyToMono(ScheduleClassDto.class)
                .switchIfEmpty(Mono.error(new RuntimeException("Lớp học đã hết slot")));
            // lấy đối tượng lịch học ấy và gọi đến selectedclassservice để check trùng lịch học đã được đăng ký hay không
        return checkCapacity.flatMap(scheduleClassDto -> {
            Mono<Boolean> checkDuplicate = webClient
                    .post()
                    .uri(ApiGateway+"/selectedclass/checkduplicate")
                    .bodyValue(scheduleClassDto)
                    .retrieve()
                    .bodyToMono(Boolean.class);
            return checkDuplicate.flatMap(isDuplicate  -> {
                if (!isDuplicate) {
                    return Mono.error(new RuntimeException("Lịch học đã bị trùng"));
                }
                else{
                    //gọi đến selectedclassservice để check môn học đã được đăng ký hay chưa
                    Mono<SelectedClassDto> checkRegister = webClient
                            .post()
                            .uri(ApiGateway+"/selectedclass/checkregister")
                            .bodyValue(scheduleClassDto)
                            .retrieve()
                            .bodyToMono(SelectedClassDto.class);
                    return checkRegister.flatMap(selectedClassDto -> {
                        if (selectedClassDto.getId()==0){
                            // nếu chưa đăng ký thì thực hiện thêm mới
                            return webClient.post()
                                    .uri(ApiGateway+"/selectedclass/save")
                                    .bodyValue(selectedClassDto)
                                    .retrieve()
                                    .bodyToMono(Void.class);
                        }
                        else{
                            // nếu đã được đăng ký rồi thì thực hiện chỉnh sửa
                            return webClient.put()
                                    .uri(ApiGateway+"/selectedclass/update/"+selectedClassDto.getId())
                                    .bodyValue(selectedClassDto)
                                    .retrieve()
                                    .bodyToMono(Void.class);
                        }
                    });
                }
            });
        });
    }
    public Mono<Void> DeleteSelectedClass(int id){
        return webClient.delete()
                .uri(ApiGateway+"/selectedclass/delete/"+id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}



