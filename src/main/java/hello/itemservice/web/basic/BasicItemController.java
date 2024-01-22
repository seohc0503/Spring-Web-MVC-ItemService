package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 멤버변수만 사용해서 생성자를 자동으로 만들어 줌 (의존관계 주입)
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

    // addItemV1
    // 상품 등록 처리 (상품 등록 클릭) // 상품 등록 폼과 url 맵핑은 갖지맡 Get/Post 방식으로 구분
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "/basic/item";
    }

    /** addItemV2
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가
     */
    // @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해 줌
    // Item 객체 생성, 파라미터 값들을 객체에 주입, 객체를 Model에 추가 <- 전부 자동으로 해줌
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능
        return "/basic/item";
    }

    /** addItemV3
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 ex) Item -> item
     **/
    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model) {
        itemRepository.save(item);
        // model.addAttribute("item", item); //자동 추가, 생략 가능
        return "/basic/item";
    }

    /** addItemV4
     * @ModelAttribute도 생략 가능
     * String과 같은 일반 타입의 경우 @RequestParam이 자동 적용
     * 클래스와 같은 타입의 경우 @ModelAttribute가 자동 적용
     * Model model도 생략 가능 -> 자동으로 Model 객체를 생성해서 값을 넣어줌
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes
     * itemId 바로 사용
     * "저장완료" 문구 추가
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    // 상품 수정 폼 Controller
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
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
