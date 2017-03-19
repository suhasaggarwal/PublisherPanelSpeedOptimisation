package com.websystique.springmvc.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.elasticsearch.plugin.nlpcn.executors.CsvExtractorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publisherdata.Daos.AggregationModule;
import com.publisherdata.model.PublisherReport;
import com.websystique.springmvc.model.Reports;

@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService{
	
	private static final AtomicLong counter = new AtomicLong();
	
//	ReportDAOImpl repDAO = ReportDAOImpl.getInstance();
	
	List<PublisherReport> report= new ArrayList<PublisherReport>();
	
	Set<String> channelNames = new HashSet<String>();
	
    String channelName = null;
	
    AggregationModule module =  AggregationModule.getInstance();
    
    public List<PublisherReport> extractReports(long id,String dateRange){
	
        String [] dateInterval = dateRange.split(",");
    	
    	
    	if(id == 1){
    	 	try {
				
    	 		module.setUp();
    	 		report=module.countBrandName(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
    	if(id == 2){
    	 	try {
    	 		module.setUp();
				report=module.countBrowser(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
    	
       if(id == 3){
    	 	try {
    	 		module.setUp();
    	 		report=module.countOS(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	

      
       if(id == 4){
   	 	try {
   	 	    module.setUp();
			report=module.countModel(dateInterval[0], dateInterval[1]);
		} catch (CsvExtractorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    return report;
   	    }
       
       
       
    	if(id == 5 ){
    	 	try {
    	 		module.setUp();
				report=module.countCity(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
    	if(id == 6 ){
    	 	try {
    	 		module.setUp();
				report=module.countfingerprint(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 7 ){
    	 	try {
    	 		module.setUp();
				report=module.countAudienceSegment(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
    	if(id == 8 ){
    	 	try {
    	 		module.setUp();
				report=module.gettimeofday(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 9 ){
    	 	try {
    	 		module.setUp();
				report=module.countPinCode(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
    	if(id == 10 ){
    	 	try {
    	 		module.setUp();
				report=module.countLatLong(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 11 ){
    	 	try {
    	 		module.setUp();
				report=module.gettimeofdayQuarter(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 12 ){
    	 	try {
    	 		module.setUp();
				report=module.gettimeofdayDaily(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
   
    	if(id == 13 ){
    	 	try {
    	 		module.setUp();
				report=module.countAgegroup(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 14 ){
    	 	try {
    	 		module.setUp();
				report=module.countGender(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 15 ){
    	 	try {
    	 		module.setUp();
				report=module.countISP(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 16 ){
    	 	try {
    	 		module.setUp();
				report=module.getOrg(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	if(id == 17 ){
    	 	try {
    	 		module.setUp();
				report=module.getdayQuarterdata(dateInterval[0], dateInterval[1]);
			} catch (CsvExtractorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return report;
    	}
    	
    	
     return report;
    
    }
    
    	 public List<PublisherReport> extractReportsChannel(long id,String dateRange, String channel){
    			
    	        String [] dateInterval = dateRange.split(",");
    	    	
    	    	
    	    	if(id == 1){
    	    	 	try {
    					
    	    	 		module.setUp();
    	    	 		report=module.countBrandNameChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 2){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countBrowserChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	
    	       if(id == 3){
    	    	 	try {
    	    	 		module.setUp();
    	    	 		report=module.countOSChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	

    	      
    	       if(id == 4){
    	   	 	try {
    	   	 	    module.setUp();
    				report=module.countModelChannel(dateInterval[0], dateInterval[1],channel);
    			} catch (CsvExtractorException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			    return report;
    	   	    }
    	       
    	       
    	       
    	    	if(id == 5 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countCityChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 6 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countfingerprintChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    /*	
    	    	if(id == 7 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countAudiencesegmentChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    */	
    	    	
    	    	if(id == 8 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.gettimeofdayChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 9 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countPinCodeChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 10 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.countLatLongChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 11 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.gettimeofdayQuarterChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 12 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.gettimeofdayDailyChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	

    	    	if(id == 13 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.getAgegroupChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	

    	    	if(id == 14 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.getGenderChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	

    	    	if(id == 15 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.getISPChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	

    	    	if(id == 16 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.getOrgChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	if(id == 17 ){
    	    	 	try {
    	    	 		module.setUp();
    					report=module.getdayQuarterdataChannel(dateInterval[0], dateInterval[1],channel);
    				} catch (CsvExtractorException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			    return report;
    	    	}
    	    	
    	    	
    	    	
    	    	
          	return report;
    

    }
		

    public Set<String> extractChannelNames(long id,String dateRange){
      
    	  String [] dateInterval = dateRange.split(",");

          if( id == 16){
        	try {
        		module.setUp();
				channelNames = module.getChannelList(dateInterval[0], dateInterval[1]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return  channelNames;
           
          }
    
          return channelNames;
    
    
    }



}
