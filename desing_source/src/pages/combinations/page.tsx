
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Combinations() {
  const navigate = useNavigate();
  const [activeFilter, setActiveFilter] = useState('all');
  const [activeTab, setActiveTab] = useState(2);

  const filters = [
    { id: 'all', label: 'Tümü' },
    { id: 'casual', label: 'Günlük' },
    { id: 'formal', label: 'Resmi' },
    { id: 'sport', label: 'Spor' },
    { id: 'party', label: 'Parti' }
  ];

  const combinations = [
    {
      id: 1,
      name: 'Klasik İş Kombini',
      style: 'formal',
      score: 96,
      items: ['Beyaz Gömlek', 'Siyah Pantolon', 'Siyah Blazer'],
      season: 'Tüm Mevsim',
      description: 'Profesyonel ve şık bir iş görünümü için mükemmel kombinasyon',
      image: 'https://readdy.ai/api/search-image?query=Professional%20business%20outfit%20combination%20on%20mannequin%2C%20white%20shirt%20with%20black%20trousers%20and%20blazer%2C%20elegant%20formal%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb001&orientation=portrait'
    },
    {
      id: 2,
      name: 'Rahat Günlük Stil',
      style: 'casual',
      score: 94,
      items: ['Gri Kazak', 'Mavi Kot Pantolon', 'Beyaz Spor Ayakkabı'],
      season: 'Sonbahar',
      description: 'Rahat ve şık bir günlük görünüm için ideal',
      image: 'https://readdy.ai/api/search-image?query=Casual%20everyday%20outfit%20combination%20on%20mannequin%2C%20grey%20sweater%20with%20blue%20jeans%2C%20comfortable%20casual%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb002&orientation=portrait'
    },
    {
      id: 3,
      name: 'Yaz Şıklığı',
      style: 'casual',
      score: 92,
      items: ['Çiçekli Elbise', 'Hasır Şapka', 'Sandalet'],
      season: 'Yaz',
      description: 'Yaz günleri için ferah ve şık bir kombinasyon',
      image: 'https://readdy.ai/api/search-image?query=Summer%20outfit%20combination%20on%20mannequin%2C%20floral%20dress%20with%20straw%20hat%2C%20elegant%20summer%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb003&orientation=portrait'
    },
    {
      id: 4,
      name: 'Spor Şık',
      style: 'sport',
      score: 90,
      items: ['Siyah Sweatshirt', 'Gri Eşofman', 'Spor Ayakkabı'],
      season: 'Tüm Mevsim',
      description: 'Spor ve rahat bir görünüm için mükemmel',
      image: 'https://readdy.ai/api/search-image?query=Sporty%20chic%20outfit%20combination%20on%20mannequin%2C%20black%20sweatshirt%20with%20grey%20joggers%2C%20athletic%20casual%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb004&orientation=portrait'
    },
    {
      id: 5,
      name: 'Akşam Şıklığı',
      style: 'party',
      score: 95,
      items: ['Siyah Elbise', 'Topuklu Ayakkabı', 'Clutch Çanta'],
      season: 'Tüm Mevsim',
      description: 'Özel davetler için zarif ve şık bir kombinasyon',
      image: 'https://readdy.ai/api/search-image?query=Evening%20party%20outfit%20combination%20on%20mannequin%2C%20elegant%20black%20dress%20with%20heels%2C%20sophisticated%20formal%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb005&orientation=portrait'
    },
    {
      id: 6,
      name: 'Kış Sıcaklığı',
      style: 'casual',
      score: 93,
      items: ['Kahverengi Kazak', 'Siyah Pantolon', 'Bot'],
      season: 'Kış',
      description: 'Soğuk havalarda sıcak ve şık kalmanın yolu',
      image: 'https://readdy.ai/api/search-image?query=Winter%20outfit%20combination%20on%20mannequin%2C%20brown%20sweater%20with%20black%20pants%20and%20boots%2C%20cozy%20winter%20wear%2C%20clean%20studio%20photography%2C%20fashion%20styling%2C%20high%20quality%20product%20display%2C%20soft%20lighting%2C%20minimalist%20background&width=375&height=450&seq=comb006&orientation=portrait'
    }
  ];

  const filteredCombinations = activeFilter === 'all' 
    ? combinations 
    : combinations.filter(c => c.style === activeFilter);

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-20">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <h1 className="text-lg font-bold text-gray-800">Kombin Önerileri</h1>
          <button 
            onClick={() => navigate('/settings')}
            className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
          >
            <i className="ri-filter-3-line text-xl text-gray-700"></i>
          </button>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Filters */}
        <div className="mb-6">
          <div className="flex gap-2 overflow-x-auto pb-2 scrollbar-hide">
            {filters.map((filter) => (
              <button
                key={filter.id}
                onClick={() => setActiveFilter(filter.id)}
                className={`flex-shrink-0 px-4 py-2.5 rounded-full text-sm font-semibold transition-all ${
                  activeFilter === filter.id
                    ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white shadow-md'
                    : 'bg-white text-gray-600 hover:bg-gray-50'
                }`}
              >
                {filter.label}
              </button>
            ))}
          </div>
        </div>

        {/* AI Suggestion Banner */}
        <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-3xl p-5 text-white mb-6 shadow-lg">
          <div className="flex items-start gap-3">
            <div className="w-12 h-12 rounded-full bg-white/20 flex items-center justify-center flex-shrink-0">
              <i className="ri-magic-line text-2xl"></i>
            </div>
            <div>
              <h3 className="font-bold mb-1">Size Özel Öneriler</h3>
              <p className="text-xs text-white/90 leading-relaxed mb-3">
                Yapay zeka, vücut tipiniz ve gardırobunuza göre en uyumlu kombinleri seçti
              </p>
              <div className="flex items-center gap-2">
                <div className="bg-white/20 rounded-full px-3 py-1">
                  <span className="text-xs font-semibold">{filteredCombinations.length} Kombin</span>
                </div>
                <div className="bg-white/20 rounded-full px-3 py-1">
                  <span className="text-xs font-semibold">%95 Uyum</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Combinations Grid */}
        <div className="space-y-4 mb-6">
          {filteredCombinations.map((combo) => (
            <div 
              key={combo.id}
              onClick={() => navigate(`/combination-detail/${combo.id}`, { state: { combo } })}
              className="bg-white rounded-3xl overflow-hidden shadow-sm hover:shadow-lg transition-all cursor-pointer"
            >
              <div className="relative">
                <img 
                  src={combo.image} 
                  alt={combo.name}
                  className="w-full h-80 object-cover object-top"
                />
                <div className="absolute top-4 right-4 bg-white/90 backdrop-blur-sm rounded-full px-3 py-1.5 flex items-center gap-1.5 shadow-lg">
                  <i className="ri-star-fill text-yellow-400 text-sm"></i>
                  <span className="text-sm font-bold text-gray-800">{combo.score}</span>
                </div>
                <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/70 to-transparent p-5">
                  <h3 className="text-lg font-bold text-white mb-1">{combo.name}</h3>
                  <p className="text-xs text-white/90">{combo.description}</p>
                </div>
              </div>
              <div className="p-4">
                <div className="flex items-center justify-between mb-3">
                  <div className="flex items-center gap-2 text-xs text-gray-600">
                    <i className="ri-price-tag-3-line"></i>
                    <span className="capitalize">{combo.style}</span>
                  </div>
                  <div className="flex items-center gap-2 text-xs text-gray-600">
                    <i className="ri-sun-line"></i>
                    <span>{combo.season}</span>
                  </div>
                </div>
                <div className="flex flex-wrap gap-2">
                  {combo.items.map((item, index) => (
                    <div key={index} className="bg-purple-50 rounded-full px-3 py-1">
                      <span className="text-xs text-purple-700 font-medium">{item}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 px-0 py-0 z-50">
        <div className="grid grid-cols-4 h-16">
          {[
            { icon: 'ri-home-5-line', label: 'Ana Sayfa', path: '/' },
            { icon: 'ri-shirt-line', label: 'Gardırop', path: '/wardrobe' },
            { icon: 'ri-palette-fill', label: 'Kombinler', path: '/combinations' },
            { icon: 'ri-user-line', label: 'Profil', path: '/profile' }
          ].map((tab, index) => (
            <button
              key={index}
              onClick={() => {
                setActiveTab(index);
                navigate(tab.path);
              }}
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
