package com.example.selectedclassservice.Service;

import com.example.selectedclassservice.Dto.ScheduleClassDto;
import com.example.selectedclassservice.Dto.SelectedClassDto;
import com.example.selectedclassservice.Model.SelectedClass;
import com.example.selectedclassservice.Repository.SelectedClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SelectedClassService {
    public static class Lich {
        private Date ngaybatdau, ngayketthuc;
        private int thu;
        private String giobatdau, gioketthuc;

        public Lich() {
        }

        public Lich(Date ngaybatdau, Date ngayketthuc, int thu, String giobatdau, String gioketthuc) {
            this.ngaybatdau = ngaybatdau;
            this.ngayketthuc = ngayketthuc;
            this.thu = thu;
            this.giobatdau = giobatdau;
            this.gioketthuc = gioketthuc;
        }

        public Date getNgaybatdau() {
            return ngaybatdau;
        }

        public void setNgaybatdau(Date ngaybatdau) {
            this.ngaybatdau = ngaybatdau;
        }

        public Date getNgayketthuc() {
            return ngayketthuc;
        }

        public void setNgayketthuc(Date ngayketthuc) {
            this.ngayketthuc = ngayketthuc;
        }

        public int getThu() {
            return thu;
        }

        public void setThu(int thu) {
            this.thu = thu;
        }

        public String getGiobatdau() {
            return giobatdau;
        }

        public void setGiobatdau(String giobatdau) {
            this.giobatdau = giobatdau;
        }

        public String getGioketthuc() {
            return gioketthuc;
        }

        public void setGioketthuc(String gioketthuc) {
            this.gioketthuc = gioketthuc;
        }

        @Override
        public String toString() {
            return "Lich{" + "ngaybatdau=" + ngaybatdau + ", ngayketthuc=" + ngayketthuc + ", thu=" + thu + ", giobatdau=" + giobatdau + ", gioketthuc=" + gioketthuc + '}';
        }
    }
    public static Lich chuyen(String input) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[] mang = input.split("thu ");
        int thu = Integer.parseInt(String.valueOf(mang[1].charAt(0)));
        String regexDate = "\\d{1,2}/\\d{1,2}/\\d{4}";
        String regexTime = "\\d{1,2}:\\d{2}";

        Pattern patternDate = Pattern.compile(regexDate);
        Pattern patternTime = Pattern.compile(regexTime);

        Matcher matcherDate = patternDate.matcher(input);
        Matcher matcherTime = patternTime.matcher(input);

        int dem =0,dem1=0;
        String ngaybatdau ="",ngayketthuc="";
        while (matcherDate.find()) {
            if(dem==0) ngaybatdau = matcherDate.group();
            else ngayketthuc = matcherDate.group();
            dem++;
        }
        Date dateStart = formatter.parse(ngaybatdau);
        Date dateEnd = formatter.parse(ngayketthuc);
        String giobatdau="", gioketthuc="";
        while (matcherTime.find()) {
            if(dem1==0) giobatdau = matcherTime.group();
            else gioketthuc = matcherTime.group();
            dem1++;
        }
        return new Lich(dateStart,dateEnd,thu,giobatdau,gioketthuc);
    }
    @Autowired
    private SelectedClassRepository selectedClassRepository;
    public SelectedClassDto convertToDto(SelectedClass selectedClass){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClassDto selectedClassDto=modelMapper.map(selectedClass,SelectedClassDto.class);
        return selectedClassDto;
    }
    public List<SelectedClassDto> getAll(){
        List<SelectedClass> selectedClasses=selectedClassRepository.findAll();
        List<SelectedClassDto> selectedClassDtos = selectedClasses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return  selectedClassDtos;
    }

    public void save(SelectedClassDto selectedClassDto){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClass selectedClass=modelMapper.map(selectedClassDto,SelectedClass.class);
        selectedClassRepository.save(selectedClass);
    }
    public void delete(int id){
        selectedClassRepository.deleteById(id);
    }
    public SelectedClassDto getSelectedClassById(int id){
        SelectedClass selectedClass=selectedClassRepository.getSelectedClassById(id);
        ModelMapper modelMapper=new ModelMapper();
        SelectedClassDto selectedClassDto=modelMapper.map(selectedClass,SelectedClassDto.class);
        return selectedClassDto;
    }
    public void update(SelectedClassDto selectedClassDto){
        ModelMapper modelMapper=new ModelMapper();
        SelectedClass selectedClass=modelMapper.map(selectedClassDto,SelectedClass.class);
        selectedClassRepository.save(selectedClass);
    }
    public boolean checkDuplicate(ScheduleClassDto scheduleClassDto)throws ParseException{
        List<SelectedClassDto> selectedClassDtos=getAll();
        for (SelectedClassDto x:selectedClassDtos){
            Lich lich1 = chuyen(x.getLichhoc());
            Lich lich2 = chuyen(scheduleClassDto.getLichhoc());
            if(lich2.getNgaybatdau().compareTo(lich1.getNgayketthuc())>=0 ||  lich2.getNgayketthuc().compareTo(lich1.getNgaybatdau())<=0){
                continue;
            }
            else {
                if(lich2.getThu()!=lich1.getThu()){
                    continue;
                }
                else {
                    if(lich2.getGiobatdau().compareTo(lich1.getGioketthuc())>=0 || lich1.getGiobatdau().compareTo(lich2.getGioketthuc())>=0){
                        continue;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public SelectedClassDto checkRegister(ScheduleClassDto scheduleClassDto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentDateAndTime = LocalDateTime.now().format(formatter);
        SelectedClassDto selectedClassDto=new SelectedClassDto();
        List<SelectedClassDto> selectedClassDtos=getAll();
        for (SelectedClassDto x:selectedClassDtos){
            if (x.getTenmh().equals(scheduleClassDto.getTenmh())){
                selectedClassDto.setId(x.getId());
                selectedClassDto.setScheduleclass_id(scheduleClassDto.getId());
                selectedClassDto.setTenmh(scheduleClassDto.getTenmh());
                selectedClassDto.setTengiangvien(scheduleClassDto.getTengiangvien());
                selectedClassDto.setTc(scheduleClassDto.getTc());
                selectedClassDto.setNhom(scheduleClassDto.getNhom());
                selectedClassDto.setMamh(scheduleClassDto.getMamh());
                selectedClassDto.setLichhoc(scheduleClassDto.getLichhoc());
                selectedClassDto.setNgaydangky(currentDateAndTime);
                return selectedClassDto;
            }
        }
        selectedClassDto.setScheduleclass_id(scheduleClassDto.getId());
        selectedClassDto.setTenmh(scheduleClassDto.getTenmh());
        selectedClassDto.setTengiangvien(scheduleClassDto.getTengiangvien());
        selectedClassDto.setTc(scheduleClassDto.getTc());
        selectedClassDto.setNhom(scheduleClassDto.getNhom());
        selectedClassDto.setMamh(scheduleClassDto.getMamh());
        selectedClassDto.setLichhoc(scheduleClassDto.getLichhoc());
        selectedClassDto.setNgaydangky(currentDateAndTime);
        return selectedClassDto;
    }
}
