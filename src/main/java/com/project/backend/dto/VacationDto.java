package com.project.backend.dto;

import java.time.LocalDate;
import java.time.Period;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VacationDto {
	//휴가 관리에 필요한 정보

//	    private Long empNum; 	//사원 번호
//
//		private String empName;	//이름
//		
//		private String dept;	//부서
//		
//		private String position;//직급
//		
//		private String vacaType; 	//휴가 종류 - vacationType
//		
//	//	private String vacaDay; //휴가 기간 = 휴가 종료일(vacaEnd) - 휴가 시작일(vacaStart)
//		
//		private Date vacaStart;	//휴가 시작일 - vacaStart
//		
//		private Date vacaEnd;	//휴가 종료일 - vacaEnd
//		
//	//	private String vacaEtc; //휴가 기간을 int형으로 바꿔 일수를 구하는 것
//
//		private String vacaReason;	//휴가 사유 - vacaReason

	 	
		private Long empNum;
	
		private String empName;	//이름
		
	    private String dept;	//부서
	    
	    private String position;//직급
	    
	    private String vacaType;//휴가 종류 - vacationType
	    
	    private LocalDate vacaStart;//휴가 시작일 - vacaStart
	    
	    private LocalDate vacaEnd;	//휴가 종료일 - vacaEnd
	    
	    private String vacaEtc;	//휴가 기간 (vacaEtc)
	    						//휴가 종료일 - 휴가 시작일
	    
	    private String vacaReason; //휴가 사유 - vacaReason
}
