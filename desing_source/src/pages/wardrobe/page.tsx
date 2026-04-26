
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const categories = [
  { id: 'all', label: 'Tümü', icon: 'ri-apps-line' },
  { id: 'top', label: 'Üst', icon: 'ri-t-shirt-line' },
  { id: 'bottom', label: 'Alt', icon: 'ri-layout-bottom-line' },
  { id: 'dress', label: 'Elbise', icon: 'ri-women-line' },
  { id: 'outerwear', label: 'Dış Giyim', icon: 'ri-shirt-line' },
  { id: 'shoes', label: 'Ayakkabı', icon: 'ri-footprint-line' },
  { id: 'accessory', label: 'Aksesuar', icon: 'ri-handbag-line' },
];

const wardrobeItems = [
  {
    id: 1, category: 'top', name: 'Beyaz Oversize Gömlek', color: 'Beyaz', season: 'Tüm Mevsim', brand: 'Zara',
    image: 'https://readdy.ai/api/search-image?query=white%20oversized%20shirt%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward001&orientation=portrait'
  },
  {
    id: 2, category: 'bottom', name: 'Slim Fit Siyah Pantolon', color: 'Siyah', season: 'Tüm Mevsim', brand: 'Mango',
    image: 'https://readdy.ai/api/search-image?query=black%20slim%20fit%20trousers%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward002&orientation=portrait'
  },
  {
    id: 3, category: 'outerwear', name: 'Camel Trençkot', color: 'Camel', season: 'Sonbahar', brand: 'H&M',
    image: 'https://readdy.ai/api/search-image?query=camel%20trench%20coat%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward003&orientation=portrait'
  },
  {
    id: 4, category: 'dress', name: 'Çiçekli Midi Elbise', color: 'Çok Renkli', season: 'Yaz', brand: 'Zara',
    image: 'https://readdy.ai/api/search-image?query=floral%20midi%20dress%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward004&orientation=portrait'
  },
  {
    id: 5, category: 'top', name: 'Gri Kaşmir Kazak', color: 'Gri', season: 'Kış', brand: 'COS',
    image: 'https://readdy.ai/api/search-image?query=grey%20cashmere%20sweater%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward005&orientation=portrait'
  },
  {
    id: 6, category: 'bottom', name: 'Yüksek Bel Kot', color: 'Mavi', season: 'Tüm Mevsim', brand: 'Levi\'s',
    image: 'https://readdy.ai/api/search-image?query=high%20waist%20blue%20denim%20jeans%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward006&orientation=portrait'
  },
  {
    id: 7, category: 'shoes', name: 'Beyaz Deri Sneaker', color: 'Beyaz', season: 'Tüm Mevsim', brand: 'Nike',
    image: 'https://readdy.ai/api/search-image?query=white%20leather%20sneaker%20shoes%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward007&orientation=portrait'
  },
  {
    id: 8, category: 'accessory', name: 'Altın Zincir Kolye', color: 'Altın', season: 'Tüm Mevsim', brand: 'Mango',
    image: 'https://readdy.ai/api/search-image?query=gold%20chain%20necklace%20jewelry%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward008&orientation=portrait'
  },
  {
    id: 9, category: 'outerwear', name: 'Siyah Deri Ceket', color: 'Siyah', season: 'Sonbahar', brand: 'Zara',
    image: 'https://readdy.ai/api/search-image?query=black%20leather%20jacket%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward009&orientation=portrait'
  },
  {
    id: 10, category: 'dress', name: 'Siyah Kokteyl Elbise', color: 'Siyah', season: 'Tüm Mevsim', brand: 'H&M',
    image: 'https://readdy.ai/api/search-image?query=black%20cocktail%20dress%20clothing%20product%20photography%2C%20clean%20white%20background%2C%20minimalist%20fashion%2C%20high%20quality%20fabric%20detail%2C%20soft%20studio%20lighting%2C%20centered%20composition%2C%20sharp%20focus&width=200&height=240&seq=ward010&orientation=portrait'
  },
];

export default function Wardrobe() {
  const navigate = useNavigate();
  const [activeCategory, setActiveCategory] = useState('all');
  const [activeTab] = useState(1);
  const [selectedItem, setSelectedItem] = useState<number | null>(null);
  const [showDetail, setShowDetail] = useState(false);

  const filtered = activeCategory === 'all'
    ? wardrobeItems
    : wardrobeItems.filter(i => i.category === activeCategory);

  const detailItem = wardrobeItems.find(i => i.id === selectedItem);

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-20">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <h1 className="text-lg font-bold text-gray-800">Gardırobum</h1>
          <div className="flex items-center gap-2">
            <button
              onClick={() => navigate('/upload')}
              className="w-9 h-9 flex items-center justify-center rounded-full bg-gradient-to-br from-purple-500 to-pink-500 shadow-md"
            >
              <i className="ri-add-line text-xl text-white"></i>
            </button>
          </div>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Stats */}
        <div className="grid grid-cols-3 gap-3 mb-5">
          {[
            { value: wardrobeItems.length, label: 'Kıyafet', icon: 'ri-t-shirt-line', color: 'from-purple-400 to-pink-400' },
            { value: 6, label: 'Kombin', icon: 'ri-palette-line', color: 'from-blue-400 to-cyan-400' },
            { value: 4, label: 'Mevsim', icon: 'ri-sun-line', color: 'from-orange-400 to-red-400' },
          ].map((s, i) => (
            <div key={i} className="bg-white rounded-2xl p-3 shadow-sm flex flex-col items-center">
              <div className={`w-9 h-9 rounded-xl bg-gradient-to-br ${s.color} flex items-center justify-center mb-2`}>
                <i className={`${s.icon} text-white text-base`}></i>
              </div>
              <span className="text-xl font-bold text-gray-800">{s.value}</span>
              <span className="text-[0.625rem] text-gray-500">{s.label}</span>
            </div>
          ))}
        </div>

        {/* AI Suggestion Banner */}
        <div
          onClick={() => navigate('/combinations')}
          className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-2xl p-4 text-white mb-5 shadow-md flex items-center gap-3 cursor-pointer"
        >
          <div className="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center flex-shrink-0">
            <i className="ri-magic-line text-xl"></i>
          </div>
          <div className="flex-1">
            <p className="font-bold text-sm">Kombin Önerisi Al</p>
            <p className="text-xs text-white/80">Yapay zeka gardırobunuzu analiz etsin</p>
          </div>
          <i className="ri-arrow-right-s-line text-2xl text-white/80"></i>
        </div>

        {/* Category Filter */}
        <div className="flex gap-2 overflow-x-auto pb-2 mb-4 scrollbar-hide -mx-5 px-5">
          {categories.map(cat => (
            <button
              key={cat.id}
              onClick={() => setActiveCategory(cat.id)}
              className={`flex-shrink-0 flex items-center gap-1.5 px-3 py-2 rounded-full text-xs font-semibold transition-all ${
                activeCategory === cat.id
                  ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white shadow-md'
                  : 'bg-white text-gray-600'
              }`}
            >
              <i className={`${cat.icon} text-sm`}></i>
              {cat.label}
            </button>
          ))}
        </div>

        {/* Items Count */}
        <div className="flex items-center justify-between mb-3">
          <span className="text-sm font-semibold text-gray-700">{filtered.length} parça</span>
          <button className="flex items-center gap-1 text-xs text-purple-600 font-semibold">
            <i className="ri-sort-desc text-sm"></i>
            Sırala
          </button>
        </div>

        {/* Grid */}
        <div className="grid grid-cols-2 gap-3">
          {filtered.map(item => (
            <div
              key={item.id}
              onClick={() => { setSelectedItem(item.id); setShowDetail(true); }}
              className="bg-white rounded-2xl overflow-hidden shadow-sm hover:shadow-md transition-all cursor-pointer"
            >
              <div className="relative">
                <img
                  src={item.image}
                  alt={item.name}
                  className="w-full h-44 object-cover object-top"
                />
                <div className="absolute top-2 right-2 bg-white/90 backdrop-blur-sm rounded-full px-2 py-0.5">
                  <span className="text-[0.6rem] font-bold text-purple-700">{item.season}</span>
                </div>
              </div>
              <div className="p-3">
                <p className="text-xs font-bold text-gray-800 leading-tight mb-1 truncate">{item.name}</p>
                <div className="flex items-center justify-between">
                  <span className="text-[0.6rem] text-gray-500">{item.brand}</span>
                  <div className="flex items-center gap-1">
                    <div
                      className="w-3 h-3 rounded-full border border-gray-200"
                      style={{ background: item.color === 'Beyaz' ? '#fff' : item.color === 'Siyah' ? '#111' : item.color === 'Gri' ? '#9ca3af' : item.color === 'Mavi' ? '#3b82f6' : item.color === 'Camel' ? '#c19a6b' : item.color === 'Altın' ? '#f59e0b' : '#e879f9' }}
                    ></div>
                    <span className="text-[0.6rem] text-gray-500">{item.color}</span>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* Empty State */}
        {filtered.length === 0 && (
          <div className="flex flex-col items-center justify-center py-16 text-center">
            <div className="w-16 h-16 rounded-full bg-purple-100 flex items-center justify-center mb-4">
              <i className="ri-t-shirt-line text-3xl text-purple-400"></i>
            </div>
            <p className="text-gray-500 text-sm">Bu kategoride kıyafet yok</p>
            <button
              onClick={() => navigate('/upload')}
              className="mt-4 bg-gradient-to-r from-purple-600 to-pink-600 text-white px-6 py-2.5 rounded-full text-sm font-semibold"
            >
              Kıyafet Ekle
            </button>
          </div>
        )}
      </div>

      {/* Detail Modal */}
      {showDetail && detailItem && (
        <div className="fixed inset-0 z-50 flex items-end">
          <div className="absolute inset-0 bg-black/40 backdrop-blur-sm" onClick={() => setShowDetail(false)}></div>
          <div className="relative w-full bg-white rounded-t-3xl p-5 z-10 max-h-[80vh] overflow-y-auto">
            <div className="w-10 h-1 bg-gray-200 rounded-full mx-auto mb-4"></div>
            <div className="flex gap-4 mb-5">
              <img
                src={detailItem.image}
                alt={detailItem.name}
                className="w-28 h-36 object-cover object-top rounded-2xl shadow-sm"
              />
              <div className="flex-1">
                <h3 className="font-bold text-gray-800 text-base mb-1">{detailItem.name}</h3>
                <p className="text-sm text-purple-600 font-semibold mb-3">{detailItem.brand}</p>
                <div className="space-y-2">
                  <div className="flex items-center gap-2">
                    <i className="ri-price-tag-3-line text-gray-400 text-sm"></i>
                    <span className="text-xs text-gray-600">{detailItem.category}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <i className="ri-sun-line text-gray-400 text-sm"></i>
                    <span className="text-xs text-gray-600">{detailItem.season}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <i className="ri-palette-line text-gray-400 text-sm"></i>
                    <span className="text-xs text-gray-600">{detailItem.color}</span>
                  </div>
                </div>
              </div>
            </div>
            <div className="grid grid-cols-2 gap-3">
              <button
                onClick={() => { setShowDetail(false); navigate('/try-on'); }}
                className="bg-gradient-to-r from-purple-600 to-pink-600 text-white py-3 rounded-2xl text-sm font-semibold flex items-center justify-center gap-2"
              >
                <i className="ri-magic-line"></i>
                Dene
              </button>
              <button
                onClick={() => setShowDetail(false)}
                className="bg-gray-100 text-gray-700 py-3 rounded-2xl text-sm font-semibold flex items-center justify-center gap-2"
              >
                <i className="ri-close-line"></i>
                Kapat
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 z-40">
        <div className="grid grid-cols-4 h-16">
          {[
            { icon: 'ri-home-5-line', label: 'Ana Sayfa', path: '/' },
            { icon: 'ri-shirt-fill', label: 'Gardırop', path: '/wardrobe' },
            { icon: 'ri-palette-line', label: 'Kombinler', path: '/combinations' },
            { icon: 'ri-user-line', label: 'Profil', path: '/settings' }
          ].map((tab, index) => (
            <button
              key={index}
              onClick={() => navigate(tab.path)}
              className={`flex flex-col items-center justify-center gap-1 transition-colors ${
                activeTab === index ? 'text-purple-600' : 'text-gray-400'
              }`}
            >
              <i className={`${tab.icon} text-xl`}></i>
              <span className="text-[0.625rem] font-medium">{tab.label}</span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
