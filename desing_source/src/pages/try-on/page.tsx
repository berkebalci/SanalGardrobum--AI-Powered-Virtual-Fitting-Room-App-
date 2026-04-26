
import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

export default function TryOn() {
  const navigate = useNavigate();
  const location = useLocation();
  const { image } = location.state || {};
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [selectedClothes, setSelectedClothes] = useState<string[]>([]);
  const [isSimulating, setIsSimulating] = useState(false);

  const categories = [
    { id: 'all', label: 'Tümü', icon: 'ri-apps-line' },
    { id: 'top', label: 'Üst', icon: 'ri-t-shirt-line' },
    { id: 'bottom', label: 'Alt', icon: 'ri-shorts-line' },
    { id: 'dress', label: 'Elbise', icon: 'ri-shirt-line' },
    { id: 'outer', label: 'Dış Giyim', icon: 'ri-hoodie-line' }
  ];

  const wardrobeItems = [
    { id: '1', name: 'Beyaz Gömlek', category: 'top', color: 'Beyaz', season: 'Tüm Mevsim', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20white%20elegant%20shirt%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth001&orientation=portrait' },
    { id: '2', name: 'Siyah Pantolon', category: 'bottom', color: 'Siyah', season: 'Tüm Mevsim', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20black%20elegant%20trousers%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth002&orientation=portrait' },
    { id: '3', name: 'Mavi Kot Ceket', category: 'outer', color: 'Mavi', season: 'İlkbahar', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20blue%20denim%20jacket%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth003&orientation=portrait' },
    { id: '4', name: 'Çiçekli Elbise', category: 'dress', color: 'Çok Renkli', season: 'Yaz', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20floral%20summer%20dress%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth004&orientation=portrait' },
    { id: '5', name: 'Gri Kazak', category: 'top', color: 'Gri', season: 'Kış', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20grey%20knit%20sweater%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth005&orientation=portrait' },
    { id: '6', name: 'Kahverengi Etek', category: 'bottom', color: 'Kahverengi', season: 'Sonbahar', image: 'https://readdy.ai/api/search-image?query=Professional%20product%20photography%20of%20brown%20elegant%20skirt%20on%20white%20background%2C%20clean%20minimalist%20style%2C%20fashion%20ecommerce%20photo%2C%20high%20quality%20clothing%20photography%2C%20soft%20lighting%2C%20no%20model%2C%20centered%20composition%2C%20sharp%20details&width=150&height=180&seq=cloth006&orientation=portrait' }
  ];

  const filteredItems = selectedCategory === 'all' 
    ? wardrobeItems 
    : wardrobeItems.filter(item => item.category === selectedCategory);

  const toggleClothSelection = (id: string) => {
    setSelectedClothes(prev => 
      prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]
    );
  };

  const handleSimulate = () => {
    if (selectedClothes.length === 0) return;
    setIsSimulating(true);
    setTimeout(() => {
      navigate('/simulation-result', { 
        state: { 
          image, 
          selectedClothes: wardrobeItems.filter(item => selectedClothes.includes(item.id))
        } 
      });
    }, 2000);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-24">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <button 
              onClick={() => navigate('/body-analysis')}
              className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
            >
              <i className="ri-arrow-left-line text-xl text-gray-700"></i>
            </button>
            <h1 className="text-lg font-bold text-gray-800">Sanal Deneme</h1>
          </div>
          <button 
            onClick={() => navigate('/wardrobe')}
            className="text-sm text-purple-600 font-semibold"
          >
            Gardırop
          </button>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Preview Image */}
        <div className="relative mb-6">
          <div className="bg-white rounded-3xl overflow-hidden shadow-lg">
            <img 
              src={image} 
              alt="Preview" 
              className="w-full h-64 object-cover object-top"
            />
            {selectedClothes.length > 0 && (
              <div className="absolute top-4 right-4 bg-purple-600 text-white rounded-full px-3 py-1.5 flex items-center gap-1.5 shadow-lg">
                <i className="ri-checkbox-circle-fill text-sm"></i>
                <span className="text-xs font-semibold">{selectedClothes.length} Seçili</span>
              </div>
            )}
          </div>
        </div>

        {/* Category Filter */}
        <div className="mb-6">
          <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-hide">
            {categories.map((cat) => (
              <button
                key={cat.id}
                onClick={() => setSelectedCategory(cat.id)}
                className={`flex-shrink-0 px-4 py-2.5 rounded-full text-sm font-semibold transition-all flex items-center gap-2 ${
                  selectedCategory === cat.id
                    ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white shadow-md'
                    : 'bg-white text-gray-600 hover:bg-gray-50'
                }`}
              >
                <i className={`${cat.icon} text-base`}></i>
                {cat.label}
              </button>
            ))}
          </div>
        </div>

        {/* Wardrobe Items */}
        <div className="mb-6">
          <h3 className="font-bold text-gray-800 mb-3 flex items-center gap-2">
            <i className="ri-handbag-line text-purple-600"></i>
            Gardırobunuz
          </h3>
          <div className="grid grid-cols-2 gap-3">
            {filteredItems.map((item) => (
              <div 
                key={item.id}
                onClick={() => toggleClothSelection(item.id)}
                className={`relative bg-white rounded-2xl overflow-hidden shadow-sm cursor-pointer transition-all ${
                  selectedClothes.includes(item.id) 
                    ? 'ring-4 ring-purple-500 shadow-lg' 
                    : 'hover:shadow-md'
                }`}
              >
                <div className="relative">
                  <img 
                    src={item.image} 
                    alt={item.name}
                    className="w-full h-44 object-cover object-top"
                  />
                  {selectedClothes.includes(item.id) && (
                    <div className="absolute inset-0 bg-purple-600/20 flex items-center justify-center">
                      <div className="w-12 h-12 rounded-full bg-purple-600 flex items-center justify-center shadow-lg">
                        <i className="ri-check-line text-2xl text-white"></i>
                      </div>
                    </div>
                  )}
                </div>
                <div className="p-3">
                  <h4 className="font-semibold text-gray-800 text-sm mb-1">{item.name}</h4>
                  <div className="flex items-center gap-2 text-xs text-gray-500">
                    <span className="flex items-center gap-1">
                      <i className="ri-palette-line"></i>
                      {item.color}
                    </span>
                    <span>•</span>
                    <span className="flex items-center gap-1">
                      <i className="ri-sun-line"></i>
                      {item.season}
                    </span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Info Card */}
        <div className="bg-gradient-to-r from-blue-500 to-cyan-500 rounded-2xl p-4 text-white">
          <div className="flex items-start gap-3">
            <div className="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center flex-shrink-0">
              <i className="ri-lightbulb-flash-line text-xl"></i>
            </div>
            <div>
              <h4 className="font-semibold mb-1">Nasıl Çalışır?</h4>
              <p className="text-xs text-white/90 leading-relaxed">
                Denemek istediğiniz kıyafetleri seçin. Yapay zeka, seçtiğiniz kıyafetleri vücudunuza uygun şekilde hizalayacak ve gerçekçi bir deneme görüntüsü oluşturacak.
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Bottom Action */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 px-5 py-4 z-50">
        <button 
          onClick={handleSimulate}
          disabled={selectedClothes.length === 0 || isSimulating}
          className={`w-full py-4 rounded-2xl font-semibold text-base shadow-lg transition-all flex items-center justify-center gap-2 ${
            selectedClothes.length > 0 && !isSimulating
              ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white hover:shadow-xl'
              : 'bg-gray-200 text-gray-400 cursor-not-allowed'
          }`}
        >
          {isSimulating ? (
            <>
              <i className="ri-loader-4-line text-xl animate-spin"></i>
              Simüle Ediliyor...
            </>
          ) : (
            <>
              <i className="ri-magic-line text-xl"></i>
              Sanal Deneme Yap ({selectedClothes.length})
            </>
          )}
        </button>
      </div>
    </div>
  );
}
