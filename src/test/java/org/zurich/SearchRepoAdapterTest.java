package org.zurich;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
@Import(SearchRepoAdapter.class)
class SearchRepoAdapterTest {
  @MockBean
  private MongoTemplate mongoTemplate;

  @Autowired
  private SearchRepoPort searchRepo;

  @Test
  void getCountsBySearchId() {
    when(mongoTemplate.findOne(any(Query.class), eq(WetsuitDocument.class))).thenReturn(createSearchDb());
    when(mongoTemplate.count(any(), eq(WetsuitDocument.class))).thenReturn(1L);
    List<WetsuitDocument> wetsuit = searchRepo.documentWetsuitByCriteria(null,null,"1");
    verify(mongoTemplate).findOne(any(Query.class), eq(WetsuitDocument.class));
    verify(mongoTemplate).count(any(Query.class), eq(WetsuitDocument.class));
    Assertions.assertNotNull(wetsuit);
  }

  @Test
  void getCountsBySearchIdNotFound() {
    when(mongoTemplate.findOne(any(Query.class), eq(WetsuitDocument.class))).thenReturn(null);
    List<WetsuitDocument> wetsuit = searchRepo.documentWetsuitByCriteria(null,null,"1");
    verify(mongoTemplate).findOne(any(Query.class), eq(WetsuitDocument.class));
    verify(mongoTemplate, times(0)).count(any(Query.class), eq(WetsuitDocument.class));
    Assertions.assertEquals(1, wetsuit.get(0).getSearchId());
  }

  @Test
  void save() {
    searchRepo.save(createSearchDb());
    verify(mongoTemplate).save(any(WetsuitDocument.class));
  }

  private WetsuitDocument createSearchDb() {
    WetsuitDocument wetsuitDocument = new WetsuitDocument();
    wetsuitDocument.setSearchId("99");
    wetsuitDocument.setBrand("BRAND TEST");
    wetsuitDocument.setModel("MODEL TEST");
    return wetsuitDocument;
  }
}