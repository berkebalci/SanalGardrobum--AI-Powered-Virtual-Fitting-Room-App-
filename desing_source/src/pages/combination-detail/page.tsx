import { useState, useEffect } from 'react';
import { useNavigate, useLocation, useParams } from 'react-router-dom';

interface ComboItem {
  id: number;
  name: string;
  style: string;
  score: number;
  items: string[];
  season: string;
  description: string;
  image: string;
}

const allCombinations: ComboItem[] = [
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

const styleLabels: Record<string, string> = {
  formal: 'Resmi',
  casual: 'Günlük',
  sport: 'Spor',
  party: 'Parti'
};

const styleColors: Record<string, string> = {
  formal: 'from-blue-500 to-indigo-600',
  casual: 'from-green-400 to-teal-500',
  sport: 'from-orange-400 to-red-500',
  party: 'from-pink-500 to-purple-600'
};

const itemDetails: Record<string, { color: string; fabric: string; icon: string }> = {
  'Beyaz Gömlek': { color: '#FFFFFF', fabric: 'Pamuk', icon: 'ri-shirt-line' },
  'Siyah Pantolon': { color: '#1a1a1a', fabric: 'Kumaş', icon: 'ri-layout-bottom-line' },
  'Siyah Blazer': { color: '#1a1a1a', fabric: 'Yün Karışım', icon: 'ri-shirt-fill' },
  'Gri Kazak': { color: '#9ca3af', fabric: 'Pamuk Karışım', icon: 'ri-shirt-line' },
  'Mavi Kot Pantolon': { color: '#3b82f6', fabric: 'Denim', icon: 'ri-layout-bottom-line' },
  'Beyaz Spor Ayakkabı': { color: '#FFFFFF', fabric: 'Sentetik', icon: 'ri-footprint-line' },
  'Çiçekli Elbise': { color: '#f9a8d4', fabric: 'Şifon', icon: 'ri-shirt-line' },
  'Hasır Şapka': { color: '#d97706', fabric: 'Hasır', icon: 'ri-sun-line' },
  'Sandalet': { color: '#92400e', fabric: 'Deri', icon: 'ri-footprint-line' },
  'Siyah Sweatshirt': { color: '#1a1a1a', fabric: 'Pamuk Fleece', icon: 'ri-shirt-line' },
  'Gri Eşofman': { color: '#9ca3af', fabric: 'Pamuk', icon: 'ri-layout-bottom-line' },
  'Spor Ayakkabı': { color: '#6366f1', fabric: 'Mesh', icon: 'ri-footprint-line' },
  'Siyah Elbise': { color: '#1a1a1a', fabric: 'Saten', icon: 'ri-shirt-line' },
  'Topuklu Ayakkabı': { color: '#1a1a1a', fabric: 'Deri', icon: 'ri-footprint-line' },
  'Clutch Çanta': { color: '#1a1a1a', fabric: 'Deri', icon: 'ri-handbag-line' },
  'Kahverengi Kazak': { color: '#92400e', fabric: 'Yün', icon: 'ri-shirt-line' },
  'Bot': { color: '#1a1a1a', fabric: 'Deri', icon: 'ri-footprint-line' }
};

const aiComments: Record<string, string[]> = {
  formal: [
    'Bu kombinasyon iş ortamında güçlü ve profesyonel bir izlenim bırakır.',
    'Renk paleti klasik ve zamansız; her toplantıda güven verir.',
    'Vücut oranlarınıza göre bu kesim sizi daha uzun gösterir.'
  ],
  casual: [
    'Günlük kullanım için mükemmel bir denge kurulmuş.',
    'Renk uyumu göz alıcı olmadan şık bir görünüm sağlıyor.',
    'Kumaş seçimleri hem konforlu hem de estetik.'
  ],
  sport: [
    'Hareket özgürlüğü ve stil bir arada sunuluyor.',
    'Renk kontrası enerjik ve dinamik bir görünüm yaratıyor.',
    'Spor aktivitelerden günlük kullanıma kolayca geçiş yapabilirsiniz.'
  ],
  party: [
    'Özel geceler için biçilmiş kaftan; zarif ve çarpıcı.',
    'Renk seçimi ışık altında muhteşem bir etki yaratır.',
    'Aksesuar tercihleri kombini tamamlıyor ve dengeli bir görünüm sunuyor.'
  ]
};

export default function CombinationDetail() {
  const navigate = useNavigate();
  const location = useLocation();
  const { id } = useParams<{ id: string }>();
  const [saved, setSaved] = useState(false);
  const [activeTab, setActiveTab] = useState<'details' | 'analysis'>('details');
  const [animateScore, setAnimateScore] = useState(false);

  const combo: ComboItem = location.state?.combo
    || allCombinations.find((c) => c.id === Number(id))
    || allCombinations[0];

  useEffect(() => {
    const timer = setTimeout(() => setAnimateScore(true), 300);
    return () => clearTimeout(timer);
  }, []);

  const handleTryOn = () => {
    navigate('/simulation-result', {
      state: {
        image: combo.image,
        selectedClothes: combo.items.map((item, index) => ({
          id: index,
          name: item,
          image: combo.image
        })),
        fromCombo: true,
        combo
      }
    });
  };

  const scoreBreakdown = [
    { label: 'Renk Uyumu', value: Math.min(combo.score + 1, 100), icon: 'ri-contrast-2-line', color: 'purple' },
    { label: 'Vücut Uygunluğu', value: Math.max(combo.score - 2, 80), icon: 'ri-body-scan-line', color: 'blue' },
    { label: 'Mevsimsellik', value: Math.min(combo.score + 2, 100), icon: 'ri-sun-line', color: 'amber' },
    { label: 'Stil Bütünlüğü', value: combo.score, icon: 'ri-magic-line', color: 'pink' }
  ];

  const colorMap: Record<string, string> = {
    purple: 'from-purple-400 to-purple-600',
    blue: 'from-blue-400 to-blue-600',
    amber: 'from-amber-400 to-amber-600',
    pink: 'from-pink-400 to-pink-600'
  };

  const textColorMap: Record<string, string> = {
    purple: 'text-purple-600',
    blue: 'text-blue-600',
    amber: 'text-amber-600',
    pink: 'text-pink-600'
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-28">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <button
              onClick={() => navigate('/combinations')}
              className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
            >
              <i className="ri-arrow-left-line text-xl text-gray-700"></i>
            </button>
            <h1 className="text-lg font-bold text-gray-800">Kombin Detayı</h1>
          </div>
          <button
            onClick={() => setSaved(!saved)}
            className={`w-9 h-9 flex items-center justify-center rounded-full transition-all ${saved ? 'bg-pink-50' : 'hover:bg-gray-100'}`}
          >
            <i className={`${saved ? 'ri-heart-fill text-pink-500' : 'ri-heart-line text-gray-700'} text-xl`}></i>
          </button>
        </div>
      </div>

      <div className="pt-20">
        {/* Hero Image */}
        <div className="relative mx-5 mb-5">
          <div className="rounded-3xl overflow-hidden shadow-xl">
            <img
              src={combo.image}
              alt={combo.name}
              className="w-full h-80 object-cover object-top"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent rounded-3xl" />
            {/* Style Badge */}
            <div className={`absolute top-4 left-4 bg-gradient-to-r ${styleColors[combo.style]} text-white rounded-full px-3 py-1.5 text-xs font-bold shadow-lg`}>
              {styleLabels[combo.style]}
            </div>
            {/* Score Badge */}
            <div className="absolute top-4 right-4 bg-white/90 backdrop-blur-sm rounded-full px-3 py-1.5 flex items-center gap-1.5 shadow-lg">
              <i className="ri-star-fill text-yellow-400 text-sm"></i>
              <span className="text-sm font-bold text-gray-800">{combo.score}</span>
            </div>
            {/* Title overlay */}
            <div className="absolute bottom-0 left-0 right-0 p-5">
              <h2 className="text-xl font-bold text-white mb-1">{combo.name}</h2>
              <div className="flex items-center gap-3">
                <div className="flex items-center gap-1.5 text-white/80 text-xs">
                  <i className="ri-sun-line"></i>
                  <span>{combo.season}</span>
                </div>
                <div className="flex items-center gap-1.5 text-white/80 text-xs">
                  <i className="ri-shirt-line"></i>
                  <span>{combo.items.length} Parça</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Description */}
        <div className="mx-5 mb-5 bg-white rounded-2xl p-4 shadow-sm">
          <p className="text-sm text-gray-600 leading-relaxed">{combo.description}</p>
        </div>

        {/* Tabs */}
        <div className="mx-5 mb-5">
          <div className="bg-white rounded-2xl p-1 flex shadow-sm">
            <button
              onClick={() => setActiveTab('details')}
              className={`flex-1 py-2.5 rounded-xl text-sm font-semibold transition-all ${activeTab === 'details' ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white shadow-md' : 'text-gray-500'}`}
            >
              Parçalar
            </button>
            <button
              onClick={() => setActiveTab('analysis')}
              className={`flex-1 py-2.5 rounded-xl text-sm font-semibold transition-all ${activeTab === 'analysis' ? 'bg-gradient-to-r from-purple-600 to-pink-600 text-white shadow-md' : 'text-gray-500'}`}
            >
              Stil Analizi
            </button>
          </div>
        </div>

        {/* Tab Content */}
        {activeTab === 'details' && (
          <div className="mx-5 space-y-3 mb-5">
            {combo.items.map((item, index) => {
              const detail = itemDetails[item] || { color: '#e5e7eb', fabric: 'Kumaş', icon: 'ri-shirt-line' };
              return (
                <div key={index} className="bg-white rounded-2xl p-4 shadow-sm flex items-center gap-4">
                  <div
                    className="w-12 h-12 rounded-xl flex items-center justify-center flex-shrink-0 shadow-inner"
                    style={{ backgroundColor: detail.color === '#FFFFFF' ? '#f3f4f6' : detail.color + '22' }}
                  >
                    <i className={`${detail.icon} text-xl`} style={{ color: detail.color === '#FFFFFF' ? '#6b7280' : detail.color }}></i>
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="font-semibold text-gray-800 text-sm">{item}</p>
                    <p className="text-xs text-gray-500 mt-0.5">{detail.fabric}</p>
                  </div>
                  <div className="flex items-center gap-1.5 flex-shrink-0">
                    <div
                      className="w-4 h-4 rounded-full border border-gray-200 shadow-sm"
                      style={{ backgroundColor: detail.color }}
                    />
                    <span className="text-xs text-gray-400">Renk</span>
                  </div>
                </div>
              );
            })}

            {/* Season & Occasion Tags */}
            <div className="bg-white rounded-2xl p-4 shadow-sm">
              <p className="text-xs font-semibold text-gray-500 uppercase tracking-wide mb-3">Kullanım Bilgisi</p>
              <div className="flex flex-wrap gap-2">
                <div className="bg-purple-50 rounded-full px-3 py-1.5 flex items-center gap-1.5">
                  <i className="ri-sun-line text-purple-500 text-xs"></i>
                  <span className="text-xs text-purple-700 font-medium">{combo.season}</span>
                </div>
                <div className="bg-blue-50 rounded-full px-3 py-1.5 flex items-center gap-1.5">
                  <i className="ri-price-tag-3-line text-blue-500 text-xs"></i>
                  <span className="text-xs text-blue-700 font-medium">{styleLabels[combo.style]}</span>
                </div>
                <div className="bg-green-50 rounded-full px-3 py-1.5 flex items-center gap-1.5">
                  <i className="ri-shirt-line text-green-500 text-xs"></i>
                  <span className="text-xs text-green-700 font-medium">{combo.items.length} Parça</span>
                </div>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'analysis' && (
          <div className="mx-5 space-y-4 mb-5">
            {/* Score Breakdown */}
            <div className="bg-white rounded-2xl p-5 shadow-sm">
              <h3 className="font-bold text-gray-800 mb-4 flex items-center gap-2 text-sm">
                <i className="ri-bar-chart-line text-purple-600"></i>
                Uyum Skorları
              </h3>
              <div className="space-y-4">
                {scoreBreakdown.map((score, index) => (
                  <div key={index}>
                    <div className="flex items-center justify-between mb-1.5">
                      <div className="flex items-center gap-2">
                        <i className={`${score.icon} ${textColorMap[score.color]} text-sm`}></i>
                        <span className="text-sm font-medium text-gray-700">{score.label}</span>
                      </div>
                      <span className="text-sm font-bold text-gray-800">{score.value}%</span>
                    </div>
                    <div className="relative h-2 bg-gray-100 rounded-full overflow-hidden">
                      <div
                        className={`absolute left-0 top-0 h-full bg-gradient-to-r ${colorMap[score.color]} rounded-full transition-all duration-1000`}
                        style={{ width: animateScore ? `${score.value}%` : '0%' }}
                      />
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Overall Score Ring */}
            <div className="bg-white rounded-2xl p-5 shadow-sm flex items-center gap-5">
              <div className="relative w-20 h-20 flex-shrink-0">
                <svg className="w-20 h-20 -rotate-90" viewBox="0 0 80 80">
                  <circle cx="40" cy="40" r="32" fill="none" stroke="#f3f4f6" strokeWidth="8" />
                  <circle
                    cx="40" cy="40" r="32" fill="none"
                    stroke="url(#scoreGrad)" strokeWidth="8"
                    strokeLinecap="round"
                    strokeDasharray={`${2 * Math.PI * 32}`}
                    strokeDashoffset={`${2 * Math.PI * 32 * (1 - (animateScore ? combo.score / 100 : 0))}`}
                    style={{ transition: 'stroke-dashoffset 1.2s ease' }}
                  />
                  <defs>
                    <linearGradient id="scoreGrad" x1="0%" y1="0%" x2="100%" y2="0%">
                      <stop offset="0%" stopColor="#a855f7" />
                      <stop offset="100%" stopColor="#ec4899" />
                    </linearGradient>
                  </defs>
                </svg>
                <div className="absolute inset-0 flex items-center justify-center">
                  <span className="text-lg font-bold text-gray-800">{combo.score}</span>
                </div>
              </div>
              <div>
                <p className="font-bold text-gray-800 mb-1">Genel Uyum Skoru</p>
                <p className="text-xs text-gray-500 leading-relaxed">
                  Bu kombinasyon yapay zeka tarafından {combo.score >= 95 ? 'mükemmel' : combo.score >= 90 ? 'çok iyi' : 'iyi'} olarak değerlendirildi.
                </p>
              </div>
            </div>

            {/* AI Comments */}
            <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-2xl p-5 text-white shadow-lg">
              <div className="flex items-center gap-2 mb-3">
                <div className="w-8 h-8 rounded-full bg-white/20 flex items-center justify-center">
                  <i className="ri-robot-line text-base"></i>
                </div>
                <p className="font-bold text-sm">Yapay Zeka Yorumu</p>
              </div>
              <div className="space-y-2">
                {(aiComments[combo.style] || aiComments.casual).map((comment, index) => (
                  <div key={index} className="flex items-start gap-2">
                    <i className="ri-checkbox-circle-line text-white/80 text-sm mt-0.5 flex-shrink-0"></i>
                    <p className="text-xs text-white/90 leading-relaxed">{comment}</p>
                  </div>
                ))}
              </div>
            </div>

            {/* Similar Combos */}
            <div className="bg-white rounded-2xl p-4 shadow-sm">
              <p className="text-xs font-semibold text-gray-500 uppercase tracking-wide mb-3">Benzer Kombinler</p>
              <div className="flex gap-3 overflow-x-auto pb-1 scrollbar-hide">
                {allCombinations
                  .filter((c) => c.id !== combo.id && c.style === combo.style)
                  .slice(0, 3)
                  .map((similar) => (
                    <div
                      key={similar.id}
                      onClick={() => navigate(`/combination-detail/${similar.id}`, { state: { combo: similar } })}
                      className="flex-shrink-0 w-24 cursor-pointer"
                    >
                      <div className="rounded-xl overflow-hidden mb-1.5">
                        <img src={similar.image} alt={similar.name} className="w-24 h-28 object-cover object-top" />
                      </div>
                      <p className="text-xs font-medium text-gray-700 truncate">{similar.name}</p>
                      <div className="flex items-center gap-1 mt-0.5">
                        <i className="ri-star-fill text-yellow-400 text-xs"></i>
                        <span className="text-xs text-gray-500">{similar.score}</span>
                      </div>
                    </div>
                  ))}
                {allCombinations.filter((c) => c.id !== combo.id && c.style === combo.style).length === 0 && (
                  <p className="text-xs text-gray-400">Bu stilde başka kombin yok.</p>
                )}
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Bottom CTA */}
      <div className="fixed bottom-0 left-0 right-0 bg-white/90 backdrop-blur-md border-t border-gray-100 px-5 py-4 z-50">
        <div className="flex gap-3">
          <button
            onClick={() => navigate('/combinations')}
            className="flex-1 py-3.5 bg-gray-100 rounded-2xl font-semibold text-sm text-gray-700 flex items-center justify-center gap-2 transition-all hover:bg-gray-200"
          >
            <i className="ri-arrow-left-line text-base"></i>
            Geri Dön
          </button>
          <button
            onClick={handleTryOn}
            className="flex-[2] py-3.5 bg-gradient-to-r from-purple-600 to-pink-600 text-white rounded-2xl font-bold text-sm flex items-center justify-center gap-2 shadow-lg hover:shadow-xl transition-all"
          >
            <i className="ri-magic-line text-base"></i>
            Sanal Dene
          </button>
        </div>
      </div>
    </div>
  );
}
