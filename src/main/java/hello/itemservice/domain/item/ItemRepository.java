package hello.itemservice.domain.item;


// 상품 도메인 개발
// 2.상품 저장소

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 저장, id로 조회, id로 전체 조회, 상품 수정
@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 사용 주의
    private static long sequence = 0L; // static

    // 상품 저장 - Id 번호 생성, store 에 <Id, 객체> 저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // id로 상품 조회
    public Item findById(long id) {
        return store.get(id);
    }

    // id로 전체 조회 - List 에 담아서 반환
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 상품 수정 - void
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
