package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    // 상품 목록 Controller
    // 모든 상품을 조회 -> 상품 객체(items) Model 에 담기 -> 뷰 템플릿 호출(Model 전달)
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    // 상품 상세 Controller
    // @PathVariable 로 전달 받은 Id를 통해 상품(객체) 찾기 -> Model 에 저장 -> 뷰 템플릿에 호출(전달)
     /** @PathVariable : url 경로에서 변수값을 추출하여 매개변수에 할당
      * 이때, 경로변수 = { } 안의 값
      * @PathVariable("itemId") long itemId 를 통해서
      * /basic/items/{itemId} 중 {itemId} 부분에 들어간 경로변수를 추출하여 long itemId에 할당할 수 있다
      */
    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 상품 등록 폼
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // 상품 등록 처리 (상품 등록 클릭)
    @PostMapping("/add")
    public String save() {
        return "xxx";
    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("item1", 10000, 10));
        itemRepository.save(new Item("item2", 20000, 20));
    }

}
