package org.insurancecompany;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@Import(SearchService.class)
class SearchServiceTest {
  @MockBean
  private SearchRepoPort searchRepo;
  @Autowired
  private SearchServiceI searchService;

  @MockBean
  private MongoTemplate mongoTemplate;

  @Test
  void postSearch() {
    searchService.save(createWetSuitModel());
    verify(mongoTemplate).save(any(WetsuitDocument.class));
    //verify(searchService).documentWetsuit(any(WetsuitDocument.class));
  }

  private WetsuitDocument createWetSuitModel() {
    WetsuitDocument wetsuitDocument = new WetsuitDocument();
    wetsuitDocument.setSearchId("99");
    wetsuitDocument.setBrand("BRAND TEST");
    wetsuitDocument.setModel("MODEL TEST");
    return wetsuitDocument;
  }
}