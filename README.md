# Bomberman

**1. Giới thiệu**
  
- Tên game: Bomberman

- Nhóm: 5

- Tên các thành viên trong nhóm: 
    
	- Nhóm trưởng:  21020796 - Bùi Thế Thuật
  
	- Thành viên:

	+ 21020791 - Nguyễn Quang Thành
              
	+ 21020799 - Triệu Thanh Tùng 
 
- Ngôn ngữ sử dụng: Java swing

- Hình ảnh : nhóm em lấy trên bài mẫu, trên mạng và tự thiết kế

- Âm thanh : nhóm em lấy ở trên mạng 

**2. Cách chơi & luật chơi**

2.1. Cách chơi: 

  - Bomber là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống (A/D/W/S) theo sự điều khiển của người chơi.

  - Enemy là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.

  - Bomb là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass.

  - Grass là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó

  - Wall là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này

  - Brick là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.

  - Portal là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.

**Các Item cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item. Thông tin về chức năng của các Item được liệt kê như dưới đây:**

  - SpeedItem Khi sử dụng Item này, Bomber sẽ được tăng vận tốc di chuyển thêm một giá trị thích hợp
  
  - FlameItem Item này giúp tăng phạm vi ảnh hưởng của Bomb khi nổ (độ dài các Flame lớn hơn)
  
  - BombItem Thông thường, nếu không có đối tượng Bomb nào đang trong trạng thái kích hoạt, Bomber sẽ được đặt và kích hoạt duy nhất một đối tượng Bomb. Item này giúp tăng số lượng Bomb có thể đặt thêm một.

2.2. Luật chơi: 

  - Người chơi có 3 mạng, mỗi lần bị bom nổ trúng hoặc chạm vào quái sẽ bị trừ 1 mạng và reset lại trò chơi
  
  - Điều kiện thắng: Tiêu diệt toàn bộ quái vật trên bản đồ và đi vào cổng
  
  - Điều kiện thua: Hết mạng
  
**3. Các thuật toán được sử dụng trong game**

  - Thuật toán A* - Pathfinding
  
  - Thuật toán va chạm

  - Thuật toán sinh ngẫu nhiên đường đi

**4. Các chức năng đã cài đặt**

  - Bản đồ, nhạc, sound effect

  - Menu

  - Quái vật:

	+ Ballom (di chuyển ngẫu nhiên)
              
	+ Korondia (di chuyển ngẫu nhiên, có khả năng đi xuyên tường)
              
	+ Oneal (di chuyển ngẫu nhiên nếu nhân vật ở xa hơn một khoảng cách nhất định hoặc không có đường đi từ bản thân nó đến nhân vật, hoặc ngược lại thì sẽ đuổi theo nhân vật)
              
	+ Ovape (giống oneal nhưng có khả năng đi xuyên tường)
              
  - Item:

	+ SpeedItem (tăng tốc người chơi)
  
	+ BombItem  (tăng số lượng bom có thể đặt đồng thời)
          
	+ FlameItem (tăng độ rộng của bom)